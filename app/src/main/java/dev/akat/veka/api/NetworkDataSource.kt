package dev.akat.veka.api

import dev.akat.veka.api.model.ApiListResponse
import dev.akat.veka.api.model.ApiResponse
import dev.akat.veka.api.model.comments.WallCommentsDto
import dev.akat.veka.api.model.posts.GroupDto
import dev.akat.veka.api.model.posts.NewsFeedDto
import dev.akat.veka.api.model.posts.WallPostsDto
import dev.akat.veka.api.model.profile.UserDto
import dev.akat.veka.api.model.upload.UploadWallPhotoFile
import dev.akat.veka.api.model.upload.UploadWallPhotoResult
import dev.akat.veka.api.model.upload.UploadWallPhotoServer
import dev.akat.veka.api.model.upload.WallPostResult
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody

interface NetworkDataSource {

    fun fetchNewsFeed(pageSize: Int, startFrom: String?): Single<ApiResponse<NewsFeedDto>>

    fun fetchCurrentUser(): Single<ApiListResponse<UserDto>>

    fun fetchGroupById(id: Int): Single<ApiListResponse<GroupDto>>

    fun fetchComments(
        postId: Int,
        ownerId: Int,
        count: Int,
        offset: Int,
    ): Single<ApiResponse<WallCommentsDto>>

    fun fetchUserPosts(ownerId: Int, count: Int, offset: Int): Single<ApiResponse<WallPostsDto>>

    fun createComment(itemId: Int, ownerId: Int, message: String): Completable

    fun addLike(itemId: Int, ownerId: Int): Completable

    fun deleteLike(itemId: Int, ownerId: Int): Completable

    fun ignoreItem(itemId: Int, ownerId: Int): Completable

    fun getWallUploadServer(): Single<ApiResponse<UploadWallPhotoServer>>

    fun createWallPost(
        message: String?,
        attachments: String?,
    ): Single<ApiResponse<WallPostResult>>

    fun saveWallPhotoPost(
        photo: String,
        server: Int,
        hash: String,
    ): Single<ApiListResponse<UploadWallPhotoResult>>

    fun uploadWallPhotoFile(
        photos: List<MultipartBody.Part>,
        url: String,
    ): Single<UploadWallPhotoFile>
}
