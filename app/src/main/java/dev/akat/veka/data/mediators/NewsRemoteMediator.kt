package dev.akat.veka.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.local.LocalDataSource
import dev.akat.veka.local.model.PageType.NEWS
import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.mappers.api.NewsListDtoMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
class NewsRemoteMediator(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val dtoMapper: NewsListDtoMapper,
) : RxRemoteMediator<Int, PostEntity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>,
    ): Single<MediatorResult> =
        Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map { getPageKey(it) }
            .flatMap { pageKey -> fetchPage(pageKey, state) }
            .onErrorReturn(MediatorResult::Error)

    private fun getPageKey(type: LoadType): String =
        when (type) {
            LoadType.REFRESH -> INIT_PAGE_KEY
            LoadType.PREPEND -> INVALID_PAGE_KEY
            LoadType.APPEND -> localDataSource.getRemoteKey(NEWS.id).nextPageKey
        }

    private fun fetchPage(
        pageKey: String,
        state: PagingState<Int, PostEntity>,
    ): Single<out MediatorResult> =
        if (pageKey == INVALID_PAGE_KEY) {
            Single.just(MediatorResult.Success(endOfPaginationReached = true))
        } else {
            fetchFromNetwork(pageKey, state)
        }

    private fun fetchFromNetwork(
        pageKey: String,
        state: PagingState<Int, PostEntity>,
    ): Single<MediatorResult> =
        networkDataSource.fetchNewsFeed(state.config.pageSize, pageKey)
            .doOnSuccess { localDataSource.saveRemoteKey(NEWS.id, it.response.nextFrom) }
            .doOnSuccess { localDataSource.deleteOldNews(getCacheTimestamp()) }
            .map { dtoMapper.map(it.response) }
            .doOnSuccess(localDataSource::addNews)
            .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
            .onErrorReturn(MediatorResult::Error)

    private fun getCacheTimestamp(): Long =
        System.currentTimeMillis() - TimeUnit.HOURS.toMillis(TIMEOUT_HOURS)

    private companion object {
        const val INVALID_PAGE_KEY = "invalid_page"
        const val INIT_PAGE_KEY = ""

        const val TIMEOUT_HOURS = 24L
    }
}
