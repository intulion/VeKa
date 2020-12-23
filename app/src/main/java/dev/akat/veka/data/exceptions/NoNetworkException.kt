package dev.akat.veka.data.exceptions

class NoNetworkException(
    message: String?,
    cause: Throwable? = null
) : RuntimeException(message, cause)
