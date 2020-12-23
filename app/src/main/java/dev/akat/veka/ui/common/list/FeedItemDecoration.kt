package dev.akat.veka.ui.common.list

import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.UNSPECIFIED
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.children
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import dev.akat.veka.R
import kotlinx.android.synthetic.main.item_post_header.view.*

class FeedItemDecoration(
    private val separatorOffset: Int,
) : ItemDecoration() {

    private var headerView: View? = null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }

        val adapter = getDecorationTypeProvider(parent)
        if (adapter != null) {
            val type = adapter.getDecorationType(position)

            outRect.top = if (type is DecorationType.Space) {
                separatorOffset
            } else {
                getHeaderView(parent).height
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = getDecorationTypeProvider(parent)
        if (adapter != null) {
            parent.children.forEach { child ->
                val childAdapterPosition = parent.getChildAdapterPosition(child)
                val type = adapter.getDecorationType(childAdapterPosition)

                if (type is DecorationType.WithText) {
                    val headerView = getHeaderView(parent)
                    headerView.postSectionHeader.text = type.text

                    c.save()
                    c.translate(0f, (child.top - headerView.height).toFloat())
                    headerView.draw(c)
                    c.restore()
                }
            }
        } else {
            super.onDraw(c, parent, state)
        }
    }

    private fun getDecorationTypeProvider(parent: RecyclerView): DecorationTypeProvider? =
        when (val adapter = parent.adapter) {
            is ConcatAdapter -> adapter.adapters.find { it is DecorationTypeProvider } as DecorationTypeProvider
            is DecorationTypeProvider -> adapter
            else -> null
        }

    private fun getHeaderView(parent: ViewGroup): View {
        if (headerView == null) {
            headerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_post_header, parent, false)

            headerView!! fixLayoutSizeIn parent
        }
        return headerView!!
    }

    private infix fun View.fixLayoutSizeIn(parent: ViewGroup) {
        if (layoutParams == null) {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }

        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, UNSPECIFIED)

        val childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
            0,
            layoutParams.width)
        val childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
            parent.paddingTop + parent.paddingBottom,
            layoutParams.height)

        measure(childWidth, childHeight)

        layout(0, 0, measuredWidth, measuredHeight)
    }
}

sealed class DecorationType {

    object Space : DecorationType()

    class WithText(
        val text: String,
    ) : DecorationType()
}

interface DecorationTypeProvider {

    fun getDecorationType(position: Int): DecorationType
}
