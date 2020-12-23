package dev.akat.veka.ui.detail.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.akat.veka.R
import dev.akat.veka.model.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
) {

    fun bind(comment: Comment) {
        with(itemView) {
            commentAvatar.clipToOutline = true
            Glide.with(context)
                .load(comment.avatarUrl)
                .into(commentAvatar)

            commentName.text = comment.sourceName
            commentText.text = comment.text
            commentDate.text = comment.getFormattedDate()

            commentLike.text = comment.getLikesText()
            commentLike.updatePadding()
        }
    }
}
