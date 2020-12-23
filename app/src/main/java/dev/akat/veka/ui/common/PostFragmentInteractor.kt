package dev.akat.veka.ui.common

import androidx.annotation.StringRes

interface PostFragmentInteractor {

    fun onPostClicked(postId: Int)

    fun onPhotoClicked(postUrl: String)

    fun onErrorOccurred(@StringRes messageRes: Int)

    fun onShareClicked(postUrl: String)

    fun onCreatePostClicked()
}
