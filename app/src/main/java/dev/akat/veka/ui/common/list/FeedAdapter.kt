package dev.akat.veka.ui.common.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import dev.akat.veka.model.Post
import dev.akat.veka.model.PostType.PHOTO_POST
import dev.akat.veka.model.PostType.TEXT_POST
import dev.akat.veka.ui.common.list.viewholders.FeedViewHolder
import dev.akat.veka.ui.common.list.viewholders.PhotoPostViewHolder
import dev.akat.veka.ui.common.list.viewholders.TextPostViewHolder
import dev.akat.veka.widget.post.PostEventCallback

class FeedAdapter(
    private val postEventCallback: PostEventCallback,
    private val feedEventCallback: FeedEventCallback,
) : ItemTouchHelperAdapter, DecorationTypeProvider, PagingDataAdapter<Post, FeedViewHolder>(
    object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun getItemViewType(position: Int): Int =
        getItem(position)?.getType()?.id ?: TEXT_POST.id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder =
        when (viewType) {
            PHOTO_POST.id -> PhotoPostViewHolder(parent, postEventCallback, feedEventCallback)
            else -> TextPostViewHolder(parent, postEventCallback, feedEventCallback)
        }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(
        holder: FeedViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        getItem(position)?.let { holder.bind(it, payloads) }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onItemDismiss(position: Int) {
        getItem(position)?.let { feedEventCallback.onItemDismiss(position, it) }
    }

    override fun onItemLike(position: Int) {
        getItem(position)?.let { feedEventCallback.onItemLiked(position, it) }
    }

    override fun getDecorationType(position: Int): DecorationType {
        if (position == NO_POSITION || itemCount == 0) {
            return DecorationType.Space
        }

        if (position == 0) {
            return DecorationType.WithText(getItem(0)!!.getFormattedDate())
        }

        val current = getItem(position)
        val previous = getItem(position - 1)

        return if (current?.getFormattedDate() == previous?.getFormattedDate()) {
            DecorationType.Space
        } else {
            DecorationType.WithText(current?.getFormattedDate().orEmpty())
        }
    }
}
