package dev.akat.veka.ui.common.list.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.akat.veka.model.Post

const val PAYLOAD_LIKE = 1

abstract class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: Post)

    abstract fun bind(item: Post, payloads: MutableList<Any>)
}
