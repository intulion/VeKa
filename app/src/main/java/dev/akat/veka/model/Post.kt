package dev.akat.veka.model

import dev.akat.veka.model.PostType.PHOTO_POST
import dev.akat.veka.model.PostType.TEXT_POST
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class Post(
    val id: Int,
    val date: Date,
    val sourceId: Int,
    val sourceName: String,
    val avatarUrl: String,
    val photoUrl: String,
    val content: String,
    var likes: Int,
    val comments: Int,
    val shares: Int,
    var isFavorite: Boolean,
) {
    fun getFormattedDate(): String {
        val now = Calendar.getInstance()
        val postDate = Calendar.getInstance()
        postDate.time = date

        return when {
            postDate.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                    postDate.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    postDate.get(Calendar.DATE) == now.get(Calendar.DATE) -> "Today"
            postDate.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                    postDate.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    postDate.get(Calendar.DATE) == now.get(Calendar.DATE) - 1 -> "Yesterday"
            else -> SimpleDateFormat(DATE_PATTERN, Locale.US).format(date)
        }
    }

    fun getLikesText() = if (likes == 0) "" else likes.toString()

    fun getCommentsText() = if (comments == 0) "" else comments.toString()

    fun getSharesText() = if (shares == 0) "" else shares.toString()

    fun setLike() {
        isFavorite = !isFavorite
        likes += if (isFavorite) 1 else -1
    }

    fun getShareUrl(): String =
        URL_TEMPLATE.format(sourceId, id)

    fun getType(): PostType =
        if (photoUrl.isEmpty()) TEXT_POST else PHOTO_POST

    private companion object {
        const val DATE_PATTERN = "dd MMMM yyyy"
        const val URL_TEMPLATE = "https://vk.com/wall%s_%s"
    }
}

enum class PostType(val id: Int) {
    TEXT_POST(0), PHOTO_POST(1)
}
