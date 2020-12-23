package dev.akat.veka.ui.feed

import androidx.lifecycle.MutableLiveData
import dev.akat.veka.data.PostRepository
import dev.akat.veka.ui.common.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FeedPagerViewModel @Inject constructor(
    private val repository: PostRepository,
) : BaseViewModel() {

    val favoritesCount = MutableLiveData<Int>()

    init {
        loadFavoritesCount()
    }

    private fun loadFavoritesCount() {
        repository.getFavoritesCount()
            .subscribeOn(Schedulers.io())
            .subscribe(favoritesCount::postValue)
            .addTo(disposable)
    }
}
