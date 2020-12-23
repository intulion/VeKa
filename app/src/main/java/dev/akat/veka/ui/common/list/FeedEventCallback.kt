package dev.akat.veka.ui.common.list

import dev.akat.veka.model.Post

interface FeedEventCallback {

    fun onItemClicked(postId: Int)

    fun onItemDismiss(position: Int, post: Post)

    fun onItemLiked(position: Int, post: Post)
}
