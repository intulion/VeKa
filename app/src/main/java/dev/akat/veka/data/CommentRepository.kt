package dev.akat.veka.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.flowable
import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.data.mediators.CommentsRemoteMediator
import dev.akat.veka.local.LocalDataSource
import dev.akat.veka.mappers.api.CommentDtoMapper
import dev.akat.veka.mappers.db.CommentEntityMapper
import dev.akat.veka.model.Comment
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val commentDtoMapper: CommentDtoMapper,
    private val commentEntityMapper: CommentEntityMapper,
    private val pagingConfig: PagingConfig,
) {
    @ExperimentalPagingApi
    fun getComments(postId: Int, ownerId: Int): Flowable<PagingData<Comment>> =
        Pager(
            config = pagingConfig,
            remoteMediator = CommentsRemoteMediator(
                networkDataSource,
                localDataSource,
                commentDtoMapper,
                postId,
                ownerId,
            ),
            pagingSourceFactory = { localDataSource.getComments(postId) }
        ).flowable
            .map { pagingData -> pagingData.map(commentEntityMapper::map) }

    fun sendComment(postId: Int, ownerId: Int, message: String): Completable =
        networkDataSource.createComment(postId, ownerId, message)
}
