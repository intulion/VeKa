package dev.akat.veka.ui.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import dev.akat.veka.R
import dev.akat.veka.data.exceptions.NoNetworkException
import dev.akat.veka.ui.common.PostFragmentInteractor

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected var postFragmentInteractor: PostFragmentInteractor? = null

    protected abstract val viewModel: BaseViewModel

    protected abstract fun initView(view: View, savedInstanceState: Bundle?)
    protected abstract fun observeData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        observeData()
        observeException()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PostFragmentInteractor) {
            postFragmentInteractor = context
        }
    }

    override fun onDetach() {
        postFragmentInteractor = null
        super.onDetach()
    }

    protected fun showMessage(@StringRes messageRes: Int) {
        (activity as BaseActivity).showMessage(messageRes)
    }

    private fun observeException() {
        viewModel.exception.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { exception ->
                handleException(exception)
            }
        }
    }

    private fun handleException(throwable: Throwable) {
        when (throwable) {
            is NoNetworkException -> (activity as BaseActivity).showNoNetworkMessage()
            else -> (activity as BaseActivity).showErrorDialog(R.string.message_request_error)
        }
    }
}
