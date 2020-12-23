package dev.akat.veka.data

import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.api.model.ApiListResponse
import dev.akat.veka.api.model.ApiResponse
import dev.akat.veka.api.model.upload.UploadWallPhotoFile
import dev.akat.veka.api.model.upload.UploadWallPhotoResult
import dev.akat.veka.api.model.upload.UploadWallPhotoServer
import dev.akat.veka.api.model.upload.WallPostResult
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class CreatePostRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    fun getWallUploadServer(): Single<ApiResponse<UploadWallPhotoServer>> =
        networkDataSource.getWallUploadServer()

    fun createWallPost(
        message: String? = null,
        attachments: String? = null,
    ): Single<ApiResponse<WallPostResult>> =
        networkDataSource.createWallPost(message, attachments)

    fun saveWallPhotoPost(
        photo: String,
        server: Int,
        hash: String,
    ): Single<ApiListResponse<UploadWallPhotoResult>> =
        networkDataSource.saveWallPhotoPost(photo, server, hash)

    fun uploadWallPhotoFile(
        photos: List<MultipartBody.Part>,
        url: String,
    ): Single<UploadWallPhotoFile> =
        networkDataSource.uploadWallPhotoFile(photos, url)
}
