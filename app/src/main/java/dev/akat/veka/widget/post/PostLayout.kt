package dev.akat.veka.widget.post

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import dev.akat.veka.model.Post

abstract class PostLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr) {

    abstract fun bindPost(post: Post, callback: PostEventCallback)

    abstract fun updateLikeButton(post: Post)
}
