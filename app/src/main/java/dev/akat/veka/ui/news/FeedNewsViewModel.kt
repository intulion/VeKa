package dev.akat.veka.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import dev.akat.veka.data.PostRepository
import dev.akat.veka.model.Post
import dev.akat.veka.ui.common.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FeedNewsViewModel @Inject constructor(
    private val repository: PostRepository,
) : BaseViewModel() {

    val postList = MutableLiveData<PagingData<Post>>()

    init {
        loadNewsFeed()
    }

    fun setLike(post: Post) {
        repository.sendLike(post.id, post.sourceId, post.isFavorite)
            .subscribeOn(Schedulers.io())
            .doOnComplete { repository.setLike(post.id, post.isFavorite) }
            .subscribeBy(::handleError)
            .addTo(disposable)
    }

    fun removePost(post: Post) {
        repository.sendIgnorePost(post.id, post.sourceId)
            .subscribeOn(Schedulers.io())
            .doOnComplete { repository.removePost(post.id) }
            .subscribeBy(::handleError)
            .addTo(disposable)
    }

    private fun loadNewsFeed() {
        @OptIn(ExperimentalPagingApi::class)
        repository.getNewsFeed()
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .subscribe(postList::postValue, ::handleError)
            .addTo(disposable)
    }
}
