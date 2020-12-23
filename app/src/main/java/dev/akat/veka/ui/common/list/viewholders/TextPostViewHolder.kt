package dev.akat.veka.ui.common.list.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.akat.veka.R
import dev.akat.veka.model.Post
import dev.akat.veka.ui.common.list.FeedEventCallback
import dev.akat.veka.widget.post.PostEventCallback
import kotlinx.android.synthetic.main.item_text_post.view.*

class TextPostViewHolder(
    parent: ViewGroup,
    private val postEventCallback: PostEventCallback,
    private val feedEventCallback: FeedEventCallback?
) : FeedViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_text_post, parent, false)
) {
    override fun bind(item: Post) {
        itemView.postPreview.bindPost(item, postEventCallback)
        itemView.setOnClickListener { feedEventCallback?.onItemClicked(item.id) }
    }

    override fun bind(item: Post, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (payloads.first()) {
                PAYLOAD_LIKE -> itemView.postPreview.updateLikeButton(item)
            }
        }
    }
}
