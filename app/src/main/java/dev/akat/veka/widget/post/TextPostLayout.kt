package dev.akat.veka.widget.post

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import dev.akat.veka.R
import dev.akat.veka.model.Post
import dev.akat.veka.widget.getHeightWithMargins
import dev.akat.veka.widget.getWidthWithMargins
import dev.akat.veka.widget.layoutWithMargins
import kotlinx.android.synthetic.main.view_text_post.view.*
import kotlin.math.max

class TextPostLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : PostLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_text_post, this, true)
        postAvatar.clipToOutline = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = paddingStart + paddingEnd
        var heightUsed = paddingTop + paddingBottom
        var height = heightUsed

        // 1. Header
        measureChildWithMargins(
            postAvatar,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed
        )

        widthUsed += postAvatar.getWidthWithMargins()
        height += postAvatar.getHeightWithMargins()

        measureChildWithMargins(
            postTitle,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed
        )
        heightUsed += postTitle.getHeightWithMargins()

        measureChildWithMargins(
            postSubtitle,
            widthMeasureSpec, widthUsed,
            heightMeasureSpec, heightUsed
        )
        heightUsed += postSubtitle.getHeightWithMargins()

        height = max(heightUsed, height)

        // 2. Content
        measureChildWithMargins(
            postContent,
            widthMeasureSpec, paddingStart + paddingEnd,
            heightMeasureSpec, height
        )
        height += postContent.getHeightWithMargins()

        // 3. Bottom buttons
        measureChildWithMargins(
            postLikeButton,
            widthMeasureSpec, 0,
            heightMeasureSpec, height
        )
        measureChildWithMargins(
            postCommentButton,
            widthMeasureSpec, 0,
            heightMeasureSpec, height
        )
        measureChildWithMargins(
            postShareButton,
            widthMeasureSpec, 0,
            heightMeasureSpec, height
        )
        height += postShareButton.getHeightWithMargins()

        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val leftWithPadding = paddingStart
        val topWithPadding = paddingTop
        val rightWithPadding = measuredWidth - paddingEnd

        // 1. Header
        var currentLeft = leftWithPadding
        var currentTop = topWithPadding

        postAvatar.layoutWithMargins(currentLeft, currentTop)
        currentLeft += postAvatar.getWidthWithMargins()

        postTitle.layoutWithMargins(
            currentLeft, currentTop,
            rightWithPadding, postTitle.measuredHeight
        )
        currentTop += postTitle.getHeightWithMargins()

        postSubtitle.layoutWithMargins(
            currentLeft, currentTop,
            rightWithPadding, postSubtitle.measuredHeight
        )
        currentTop += postSubtitle.getHeightWithMargins()

        currentTop = max(currentTop, topWithPadding + postAvatar.getHeightWithMargins())

        // 2. Content
        currentLeft = leftWithPadding
        postContent.layoutWithMargins(
            currentLeft, currentTop,
            rightWithPadding, postContent.measuredHeight
        )
        currentTop += postContent.getHeightWithMargins()

        // 3. Buttons
        currentLeft = leftWithPadding

        postLikeButton.layoutWithMargins(currentLeft, currentTop)
        currentLeft += postLikeButton.getWidthWithMargins()

        postCommentButton.layoutWithMargins(currentLeft, currentTop)
        currentLeft += postCommentButton.getWidthWithMargins()

        postShareButton.layoutWithMargins(currentLeft, currentTop)
    }

    override fun generateLayoutParams(attrs: AttributeSet?) =
        MarginLayoutParams(context, attrs)

    override fun generateLayoutParams(p: LayoutParams?) = MarginLayoutParams(p)

    override fun generateDefaultLayoutParams() = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams

    override fun bindPost(post: Post, callback: PostEventCallback) {
        postTitle.text = post.sourceName
        postSubtitle.text = post.getFormattedDate()
        postContent.text = post.content

        Glide.with(context)
            .load(post.avatarUrl)
            .into(postAvatar)

        updateLikeButton(post)
        postLikeButton.setOnClickListener { callback.onLikeClicked(post) }

        with(postCommentButton) {
            text = post.getCommentsText()
            setOnClickListener { callback.onCommentClicked(post) }
        }

        with(postShareButton) {
            text = post.getSharesText()
            setOnClickListener { callback.onShareClicked(post) }
        }
    }

    override fun updateLikeButton(post: Post) {
        with(postLikeButton as MaterialButton) {
            setIconResource(
                if (post.isFavorite) R.drawable.ic_like_fill_24 else R.drawable.ic_like_24
            )
            text = post.getLikesText()
        }
    }
}
