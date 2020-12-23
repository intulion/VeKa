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
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("method/newsfeed.get?filters=post")
    fun getNewsFeed(
        @Query("count") count: Int,
        @Query("start_from") startFrom: String? = null,
    ): Single<ApiResponse<NewsFeedDto>>

    @POST("method/likes.add?type=post")
    fun addLike(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int,
    ): Completable

    @POST("method/likes.delete?type=post")
    fun deleteLike(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int,
    ): Completable

    @POST("method/newsfeed.ignoreItem?type=wall")
    fun ignoreItem(
        @Query("item_id") itemId: Int,
        @Query("owner_id") ownerId: Int,
    ): Completable

    @GET("method/users.get?" +
            "fields=domain,first_name,last_name,photo_100,about,bdate," +
            "city,country,career,education,counters,last_seen"
    )
    fun getCurrentUser(): Single<ApiListResponse<UserDto>>

    @GET("method/groups.getById")
    fun getGroupById(
        @Query("group_ids") id: Int,
    ): Single<ApiListResponse<GroupDto>>

    @GET("method/wall.getComments?sort=asc&need_likes=1&extended=1")
    fun getWallComments(
        @Query("post_id") postId: Int,
        @Query("owner_id") ownerId: Int,
        @Query("count") count: Int,
        @Query("offset") offset: Int,
    ): Single<ApiResponse<WallCommentsDto>>

    @FormUrlEncoded
    @POST("method/wall.createComment")
    fun createComment(
        @Field("post_id") postId: Int,
        @Field("owner_id") ownerId: Int,
        @Field("message") message: String,
    ): Completable

    @GET("method/wall.get?filter=owner&extended=1")
    fun getWallPosts(
        @Query("owner_id") ownerId: Int,
        @Query("count") count: Int,
        @Query("offset") offset: Int,
    ): Single<ApiResponse<WallPostsDto>>

    @GET("method/photos.getWallUploadServer")
    fun getWallUploadServer(): Single<ApiResponse<UploadWallPhotoServer>>

    @FormUrlEncoded
    @POST("method/wall.post")
    fun createWallPost(
        @Field("message") message: String?,
        @Field("attachments") attachments: String?,
    ): Single<ApiResponse<WallPostResult>>

    @FormUrlEncoded
    @POST("method/photos.saveWallPhoto")
    fun saveWallPhotoPost(
        @Field("photo") photo: String,
        @Field("server") server: Int,
        @Field("hash") hash: String,
    ): Single<ApiListResponse<UploadWallPhotoResult>>

    @Multipart
    @POST
    fun uploadWallPhotoFile(
        @Part photos: List<MultipartBody.Part>,
        @Url url: String,
    ): Single<UploadWallPhotoFile>
}
