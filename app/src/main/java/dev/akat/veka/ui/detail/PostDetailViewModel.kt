package dev.akat.veka.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import dev.akat.veka.data.CommentRepository
import dev.akat.veka.data.PostRepository
import dev.akat.veka.model.Comment
import dev.akat.veka.model.Post
import dev.akat.veka.ui.common.base.BaseViewModel
import dev.akat.veka.utils.Event
import dev.akat.veka.utils.Result
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) : BaseViewModel() {

    val currentPost = MutableLiveData<Post>()
    val commentList = MutableLiveData<PagingData<Comment>>()
    val state = MutableLiveData<Event<Result>>()

    fun observePost(postId: Int) {
        postRepository.getCachedPost(postId)
            .subscribeOn(Schedulers.io())
            .doOnNext(::loadComments)
            .subscribe(currentPost::postValue, ::handleError)
            .addTo(disposable)
    }

    fun setLike(post: Post) {
        postRepository.sendLike(post.id, post.sourceId, post.isFavorite)
            .subscribeOn(Schedulers.io())
            .doOnComplete { postRepository.setLike(post.id, post.isFavorite) }
            .subscribeBy(::handleError)
            .addTo(disposable)
    }

    private fun loadComments(post: Post) {
        @OptIn(ExperimentalPagingApi::class)
        commentRepository.getComments(post.id, post.sourceId)
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .subscribe(commentList::postValue, ::handleError)
            .addTo(disposable)
    }

    fun sendComment(post: Post, message: String) {
        commentRepository.sendComment(post.id, post.sourceId, message)
            .subscribeOn(Schedulers.io())
            .subscribe({
                state.postValue(Event(Result.Success))
            }, {
                handleError(it)
            })
            .addTo(disposable)
    }
}
