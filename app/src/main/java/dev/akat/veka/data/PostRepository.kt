package dev.akat.veka.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.flowable
import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.data.mediators.NewsRemoteMediator
import dev.akat.veka.data.mediators.WallPostsRemoteMediator
import dev.akat.veka.local.LocalDataSource
import dev.akat.veka.mappers.api.NewsListDtoMapper
import dev.akat.veka.mappers.api.PostListDtoMapper
import dev.akat.veka.mappers.db.PostEntityMapper
import dev.akat.veka.model.Post
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val newsListDtoMapper: NewsListDtoMapper,
    private val postListDtoMapper: PostListDtoMapper,
    private val postEntityMapper: PostEntityMapper,
    private val pagingConfig: PagingConfig,
) {

    fun sendLike(postId: Int, ownerId: Int, isLiked: Boolean): Completable =
        if (isLiked) {
            networkDataSource.deleteLike(postId, ownerId)
        } else {
            networkDataSource.addLike(postId, ownerId)
        }

    fun sendIgnorePost(postId: Int, ownerId: Int): Completable =
        networkDataSource.ignoreItem(postId, ownerId)

    fun getCachedPost(postId: Int): Observable<Post> =
        localDataSource.getPost(postId)
            .map(postEntityMapper::map)

    fun getFavoritesCount(): Observable<Int> =
        localDataSource.getFavoritesCount()

    fun setLike(postId: Int, isLiked: Boolean) {
        localDataSource.setLike(postId, !isLiked)
    }

    fun removePost(postId: Int) {
        localDataSource.removePost(postId)
    }

    @ExperimentalPagingApi
    fun getNewsFeed(): Flowable<PagingData<Post>> = Pager(
        config = pagingConfig,
        remoteMediator = NewsRemoteMediator(
            networkDataSource,
            localDataSource,
            newsListDtoMapper
        ),
        pagingSourceFactory = { localDataSource.getNewsFeed() }
    ).flowable
        .map { pagingData -> pagingData.map(postEntityMapper::map) }

    fun getFavorites(): Flowable<PagingData<Post>> =
        Pager(
            config = pagingConfig,
            pagingSourceFactory = { localDataSource.getFavoritePosts() }
        ).flowable
            .map { pagingData -> pagingData.map(postEntityMapper::map) }

    @ExperimentalPagingApi
    fun getWallPosts(ownerId: Int): Flowable<PagingData<Post>> =
        Pager(
            config = pagingConfig,
            remoteMediator = WallPostsRemoteMediator(
                networkDataSource,
                localDataSource,
                postListDtoMapper,
                ownerId,
            ),
            pagingSourceFactory = { localDataSource.getUserPosts(ownerId) }
        ).flowable
            .map { pagingData -> pagingData.map(postEntityMapper::map) }
}
