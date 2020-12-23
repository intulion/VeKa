package dev.akat.veka.ui.post

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import dev.akat.veka.R
import dev.akat.veka.api.model.ApiListResponse
import dev.akat.veka.api.model.ApiResponse
import dev.akat.veka.api.model.upload.UploadWallPhotoFile
import dev.akat.veka.api.model.upload.UploadWallPhotoResult
import dev.akat.veka.api.model.upload.UploadWallPhotoServer
import dev.akat.veka.api.model.upload.WallPostResult
import dev.akat.veka.api.model.upload.getAttachmentIds
import dev.akat.veka.data.CreatePostRepository
import dev.akat.veka.mappers.UriFileMapper
import dev.akat.veka.ui.common.base.BaseViewModel
import dev.akat.veka.utils.Event
import dev.akat.veka.utils.Result
import dev.akat.veka.utils.Result.Error
import dev.akat.veka.utils.Result.Loading
import dev.akat.veka.utils.Result.Success
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class CreatePostViewModel @Inject constructor(
    private val repository: CreatePostRepository,
    private val uriMapper: UriFileMapper,
) : BaseViewModel() {

    val state = MutableLiveData<Event<Result>>()

    fun createPost(message: String, photoUriList: List<Uri>) {
        if (message.isBlank() && photoUriList.isEmpty()) {
            state.value = Event(Error(R.string.message_post_error))
            return
        }

        state.value = Event(Loading)

        if (photoUriList.isNotEmpty()) {
            uploadPhotoPost(message, photoUriList)
        } else {
            uploadTextPost(message)
        }
    }

    private fun uploadTextPost(message: String) {
        repository.createWallPost(message)
            .subscribeOn(Schedulers.io())
            .subscribe({
                state.postValue(Event(Success))
            }, {
                state.postValue(Event(Error(R.string.message_request_error)))
            })
            .addTo(disposable)
    }

    private fun uploadPhotoPost(message: String, photoUriList: List<Uri>) {
        repository.getWallUploadServer()
            .subscribeOn(Schedulers.io())
            .flatMap { uploadPhotos(photoUriList, it.response) }
            .flatMap { saveUploadedPhotos(it) }
            .flatMap { createWallPost(message, it.response) }
            .subscribe({
                state.postValue(Event(Success))
            }, {
                state.postValue(Event(Error(R.string.message_request_error)))
            })
            .addTo(disposable)
    }

    private fun uploadPhotos(
        photoUriList: List<Uri>,
        server: UploadWallPhotoServer,
    ): Single<UploadWallPhotoFile> {
        val parts = photoUriList
            .map(uriMapper::map)
            .mapIndexed { index, file ->
                MultipartBody.Part.createFormData(
                    "file$index",
                    file.name,
                    file.asRequestBody(MultipartBody.FORM)
                )
            }
        return repository.uploadWallPhotoFile(parts, server.uploadUrl)
    }

    private fun saveUploadedPhotos(data: UploadWallPhotoFile): Single<ApiListResponse<UploadWallPhotoResult>> =
        repository.saveWallPhotoPost(data.photo, data.server, data.hash)

    private fun createWallPost(
        message: String,
        photoList: List<UploadWallPhotoResult>,
    ): Single<ApiResponse<WallPostResult>> =
        repository.createWallPost(message, photoList.getAttachmentIds())
}
