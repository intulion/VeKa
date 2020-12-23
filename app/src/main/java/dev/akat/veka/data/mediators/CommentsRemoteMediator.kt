package dev.akat.veka.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.local.LocalDataSource
import dev.akat.veka.local.model.CommentEntity
import dev.akat.veka.local.model.PageType.COMMENTS
import dev.akat.veka.mappers.api.CommentDtoMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

@ExperimentalPagingApi
class CommentsRemoteMediator(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val dtoMapper: CommentDtoMapper,
    private val postId: Int,
    private val ownerId: Int,
) : RxRemoteMediator<Int, CommentEntity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, CommentEntity>,
    ): Single<MediatorResult> =
        Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map { getPageOffset(it) }
            .flatMap { offset -> fetchPage(offset, state) }
            .onErrorReturn(MediatorResult::Error)

    private fun getPageOffset(loadType: LoadType): Int =
        when (loadType) {
            LoadType.REFRESH -> INIT_PAGE_OFFSET
            LoadType.PREPEND -> INVALID_PAGE
            LoadType.APPEND -> localDataSource.getRemoteOffset(COMMENTS.id, postId).nextOffset
        }

    private fun fetchPage(
        offset: Int,
        state: PagingState<Int, CommentEntity>,
    ): Single<out MediatorResult> =
        if (offset == INVALID_PAGE) {
            Single.just(MediatorResult.Success(endOfPaginationReached = true))
        } else {
            fetchFromNetwork(offset, state)
        }

    private fun fetchFromNetwork(
        offset: Int,
        state: PagingState<Int, CommentEntity>,
    ): Single<MediatorResult> =
        networkDataSource.fetchComments(postId, ownerId, state.config.pageSize, offset)
            .doOnSuccess {
                localDataSource.saveRemoteOffset(COMMENTS.id, postId,
                    offset + it.response.items.size)
            }
            .doOnSuccess { localDataSource.addComments(dtoMapper.map(it.response)) }
            .map<MediatorResult> {
                MediatorResult.Success(
                    endOfPaginationReached = it.response.items.size < state.config.pageSize
                )
            }
            .onErrorReturn(MediatorResult::Error)

    private companion object {
        const val INVALID_PAGE = -1
        const val INIT_PAGE_OFFSET = 0
    }
}
