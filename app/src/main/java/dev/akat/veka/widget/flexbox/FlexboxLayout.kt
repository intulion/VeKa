package dev.akat.veka.widget.flexbox

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.children
import dev.akat.veka.widget.getHeightWithMargins
import dev.akat.veka.widget.getWidthWithMargins
import dev.akat.veka.widget.layoutWithMargins
import kotlin.math.max

class FlexboxLayout : ViewGroup {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setWillNotDraw(true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0
        var currentRowWidth = 0
        var currentRowHeight = 0

        children.forEach { child ->
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, height)
            val childWidth = child.getWidthWithMargins()
            val childHeight = child.getHeightWithMargins()

            currentRowWidth += childWidth

            if (currentRowWidth > desiredWidth) {
                height += currentRowHeight

                currentRowWidth = childWidth
                currentRowHeight = childHeight
            }
            currentRowHeight = max(currentRowHeight, childHeight)
        }

        height += currentRowHeight + paddingTop + paddingBottom
        setMeasuredDimension(desiredWidth, resolveSize(height, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentLeft = paddingStart
        var currentTop = paddingTop
        var currentRowHeight = 0

        children.forEach { child ->
            val childWidth = child.getWidthWithMargins()
            val childHeight = child.getHeightWithMargins()

            if (currentLeft + childWidth > measuredWidth) {
                currentLeft = paddingStart
                currentTop += currentRowHeight
                currentRowHeight = 0
            }
            currentRowHeight = max(currentRowHeight, childHeight)

            child.layoutWithMargins(currentLeft, currentTop)
            currentLeft += childWidth
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?) =
        MarginLayoutParams(context, attrs)

    override fun generateLayoutParams(p: LayoutParams?) = MarginLayoutParams(p)

    override fun generateDefaultLayoutParams() = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams
}
