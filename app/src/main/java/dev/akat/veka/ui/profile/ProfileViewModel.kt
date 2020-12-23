package dev.akat.veka.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import dev.akat.veka.data.PostRepository
import dev.akat.veka.data.ProfileRepository
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.model.Post
import dev.akat.veka.model.Profile
import dev.akat.veka.ui.common.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val postRepository: PostRepository,
) : BaseViewModel() {

    val profile = MutableLiveData<Profile>()
    val postList = MutableLiveData<PagingData<Post>>()

    init {
        observeProfile()
        loadProfile()
    }

    fun setLike(post: Post) {
        postRepository.sendLike(post.id, post.sourceId, post.isFavorite)
            .subscribeOn(Schedulers.io())
            .doOnComplete { postRepository.setLike(post.id, post.isFavorite) }
            .subscribeBy(::handleError)
            .addTo(disposable)
    }

    fun removePost(post: Post) {
        postRepository.sendIgnorePost(post.id, post.sourceId)
            .subscribeOn(Schedulers.io())
            .doOnComplete { postRepository.removePost(post.id) }
            .subscribeBy(::handleError)
            .addTo(disposable)
    }

    private fun observeProfile() {
        profileRepository.getUserProfile()
            .subscribeOn(Schedulers.io())
            .subscribe(profile::postValue, ::handleError)
            .addTo(disposable)
    }

    private fun loadProfile() {
        profileRepository.fetchUserProfile()
            .subscribeOn(Schedulers.io())
            .doOnSuccess(profileRepository::saveProfile)
            .doOnSuccess(::loadPosts)
            .subscribeBy(::handleError)
            .addTo(disposable)
    }

    private fun loadPosts(profile: ProfileEntity) {
        @OptIn(ExperimentalPagingApi::class)
        postRepository.getWallPosts(profile.id)
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .subscribe(postList::postValue, ::handleError)
            .addTo(disposable)
    }
}
