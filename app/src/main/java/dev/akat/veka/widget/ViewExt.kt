package dev.akat.veka.widget

import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

fun View.layoutWithMargins(left: Int, top: Int, width: Int, height: Int) {
    val leftWithMargins = left + this.marginStart
    val rightWithMargins = width - this.marginEnd
    val topWithMargins = top + this.marginTop

    this.layout(
        leftWithMargins,
        topWithMargins,
        rightWithMargins,
        height + topWithMargins
    )
}

fun View.layoutWithMargins(left: Int, top: Int) {
    val leftWithMargins = left + this.marginStart
    val topWithMargins = top + this.marginTop

    this.layout(
        leftWithMargins,
        topWithMargins,
        leftWithMargins + this.measuredWidth,
        topWithMargins + this.measuredHeight
    )
}

fun View.getWidthWithMargins(): Int {
    return this.measuredWidth + this.marginStart + this.marginEnd
}

fun View.getHeightWithMargins(): Int {
    return this.measuredHeight + this.marginTop + this.marginBottom
}
