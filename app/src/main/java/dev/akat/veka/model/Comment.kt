package dev.akat.veka.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class Comment(
    val id: Int,
    val date: Date,
    val postId: Int,
    val ownerId: Int,
    val sourceId: Int,
    val sourceName: String,
    val avatarUrl: String,
    val text: String,
    var likes: Int,
) {
    fun getFormattedDate(): String {
        val now = Calendar.getInstance()
        val commentDate = Calendar.getInstance()
        commentDate.time = date

        return when {
            commentDate.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                    commentDate.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    commentDate.get(Calendar.DATE) == now.get(Calendar.DATE) -> "Today"
            commentDate.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                    commentDate.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    commentDate.get(Calendar.DATE) == now.get(Calendar.DATE) - 1 -> "Yesterday"
            else -> SimpleDateFormat(DATE_PATTERN, Locale.US).format(date)
        }
    }

    fun getLikesText() = if (likes == 0) "" else likes.toString()

    private companion object {
        const val DATE_PATTERN = "dd MMMM yyyy"
    }
}
