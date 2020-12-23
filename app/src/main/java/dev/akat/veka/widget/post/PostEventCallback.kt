package dev.akat.veka.widget.post

import dev.akat.veka.model.Post

interface PostEventCallback {

    fun onPhotoClicked(post: Post)

    fun onLikeClicked(post: Post)

    fun onCommentClicked(post: Post)

    fun onShareClicked(post: Post)
}
