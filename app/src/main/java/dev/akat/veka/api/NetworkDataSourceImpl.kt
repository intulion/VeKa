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
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    NetworkDataSource {

    override fun fetchNewsFeed(
        pageSize: Int,
        startFrom: String?,
    ): Single<ApiResponse<NewsFeedDto>> =
        apiService.getNewsFeed(pageSize, startFrom)

    override fun addLike(itemId: Int, ownerId: Int): Completable =
        apiService.addLike(itemId, ownerId)

    override fun deleteLike(itemId: Int, ownerId: Int): Completable =
        apiService.deleteLike(itemId, ownerId)

    override fun ignoreItem(itemId: Int, ownerId: Int): Completable =
        apiService.ignoreItem(itemId, ownerId)

    override fun fetchCurrentUser(): Single<ApiListResponse<UserDto>> =
        apiService.getCurrentUser()

    override fun fetchGroupById(id: Int): Single<ApiListResponse<GroupDto>> =
        apiService.getGroupById(id)

    override fun fetchComments(
        postId: Int,
        ownerId: Int,
        count: Int,
        offset: Int,
    ): Single<ApiResponse<WallCommentsDto>> =
        apiService.getWallComments(postId, ownerId, count, offset)

    override fun fetchUserPosts(
        ownerId: Int,
        count: Int,
        offset: Int,
    ): Single<ApiResponse<WallPostsDto>> =
        apiService.getWallPosts(ownerId, count, offset)

    override fun createComment(itemId: Int, ownerId: Int, message: String): Completable =
        apiService.createComment(itemId, ownerId, message)

    override fun getWallUploadServer(): Single<ApiResponse<UploadWallPhotoServer>> =
        apiService.getWallUploadServer()

    override fun createWallPost(
        message: String?,
        attachments: String?,
    ): Single<ApiResponse<WallPostResult>> =
        apiService.createWallPost(message, attachments)

    override fun saveWallPhotoPost(
        photo: String,
        server: Int,
        hash: String,
    ): Single<ApiListResponse<UploadWallPhotoResult>> =
        apiService.saveWallPhotoPost(photo, server, hash)

    override fun uploadWallPhotoFile(
        photos: List<MultipartBody.Part>,
        url: String,
    ): Single<UploadWallPhotoFile> =
        apiService.uploadWallPhotoFile(photos, url)
}
