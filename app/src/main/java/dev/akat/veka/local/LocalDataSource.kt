package dev.akat.veka.local

import androidx.paging.PagingSource
import dev.akat.veka.local.model.CommentEntity
import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.local.model.RemoteKeyEntity
import dev.akat.veka.local.model.RemoteOffsetEntity
import io.reactivex.Observable

interface LocalDataSource {

    fun getNewsFeed(): PagingSource<Int, PostEntity>

    fun addNews(list: List<PostEntity>)

    fun deleteOldNews(olderThan: Long)

    fun getFavoritePosts(): PagingSource<Int, PostEntity>

    fun getUserPosts(sourceId: Int): PagingSource<Int, PostEntity>

    fun getFavoritesCount(): Observable<Int>

    fun getPost(postId: Int): Observable<PostEntity>

    fun addPosts(list: List<PostEntity>)

    fun setLike(postId: Int, isLike: Boolean)

    fun removePost(postId: Int)

    fun getRemoteKey(pageType: Int): RemoteKeyEntity

    fun saveRemoteKey(pageType: Int, startFrom: String)

    fun getRemoteOffset(pageType: Int, pageOwner: Int): RemoteOffsetEntity

    fun saveRemoteOffset(pageType: Int, pageOwner: Int, nextOffset: Int)

    fun getProfile(): Observable<ProfileEntity>

    fun addProfile(profile: ProfileEntity)

    fun getComments(postId: Int): PagingSource<Int, CommentEntity>

    fun addComments(list: List<CommentEntity>)
}
