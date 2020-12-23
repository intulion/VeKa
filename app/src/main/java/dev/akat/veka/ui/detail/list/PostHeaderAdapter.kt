package dev.akat.veka.ui.detail.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.akat.veka.model.Post
import dev.akat.veka.model.PostType.PHOTO_POST
import dev.akat.veka.model.PostType.TEXT_POST
import dev.akat.veka.ui.common.list.viewholders.FeedViewHolder
import dev.akat.veka.ui.common.list.viewholders.PhotoPostViewHolder
import dev.akat.veka.ui.common.list.viewholders.TextPostViewHolder
import dev.akat.veka.widget.post.PostEventCallback

class PostHeaderAdapter(
    private val postEventCallback: PostEventCallback,
) : RecyclerView.Adapter<FeedViewHolder>() {

    var item: Post? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int =
        item?.getType()?.id ?: Int.MIN_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder =
        when (viewType) {
            TEXT_POST.id -> TextPostViewHolder(parent, postEventCallback, null)
            PHOTO_POST.id -> PhotoPostViewHolder(parent, postEventCallback, null)
            else -> throw IllegalArgumentException("Invalid post type received")
        }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        item?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int =
        if (item != null) 1 else 0
}
