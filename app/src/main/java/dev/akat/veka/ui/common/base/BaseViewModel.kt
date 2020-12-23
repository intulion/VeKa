package dev.akat.veka.ui.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.akat.veka.utils.Event
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposable by lazy { CompositeDisposable() }

    val exception = MutableLiveData<Event<Throwable>>()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    protected fun handleError(throwable: Throwable) {
        exception.postValue(Event(throwable))
    }
}
