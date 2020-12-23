package dev.akat.veka.utils

import androidx.annotation.StringRes

sealed class Result {
    object Success : Result()
    object Loading : Result()
    data class Error(@StringRes val messageRes: Int) : Result()
}
