package dev.akat.veka.local

import androidx.paging.PagingSource
import dev.akat.veka.local.db.AppDatabase
import dev.akat.veka.local.model.CommentEntity
import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.local.model.RemoteKeyEntity
import dev.akat.veka.local.model.RemoteOffsetEntity
import io.reactivex.Observable
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val database: AppDatabase) : LocalDataSource {

    override fun getNewsFeed(): PagingSource<Int, PostEntity> =
        database.newsDao().getNews()

    override fun addNews(list: List<PostEntity>) {
        database.newsDao().addNews(list)
    }

    override fun deleteOldNews(olderThan: Long) {
        database.newsDao().deleteOldNews(olderThan)
    }

    override fun getFavoritePosts(): PagingSource<Int, PostEntity> =
        database.postDao().getFavoritePosts()

    override fun getUserPosts(sourceId: Int): PagingSource<Int, PostEntity> =
        database.postDao().getBySourceId(sourceId)

    override fun getFavoritesCount(): Observable<Int> =
        database.postDao().getFavoritesCount()

    override fun getPost(postId: Int): Observable<PostEntity> =
        database.postDao().getById(postId)

    override fun addPosts(list: List<PostEntity>) {
        database.postDao().insertPosts(list)
    }

    override fun setLike(postId: Int, isLike: Boolean) {
        database.postDao().setLike(postId, isLike)
    }

    override fun removePost(postId: Int) {
        database.postDao().deletePost(postId)
    }

    override fun getRemoteKey(pageType: Int): RemoteKeyEntity =
        database.remoteKeyDao().getByPageType(pageType)

    override fun saveRemoteKey(pageType: Int, startFrom: String) {
        database.remoteKeyDao().insert(RemoteKeyEntity(pageType, startFrom))
    }

    override fun getRemoteOffset(pageType: Int, pageOwner: Int): RemoteOffsetEntity =
        database.remoteOffsetDao().get(pageType, pageOwner)

    override fun saveRemoteOffset(pageType: Int, pageOwner: Int, nextOffset: Int) {
        database.remoteOffsetDao().insert(RemoteOffsetEntity(pageType, pageOwner, nextOffset))
    }

    override fun getProfile(): Observable<ProfileEntity> =
        database.profileDao().getProfile()

    override fun addProfile(profile: ProfileEntity) {
        database.profileDao().insertProfile(profile)
    }

    override fun getComments(postId: Int): PagingSource<Int, CommentEntity> =
        database.commentDao().getCommentsByPost(postId)

    override fun addComments(list: List<CommentEntity>) {
        database.commentDao().insertComments(list)
    }
}
