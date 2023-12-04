package me.utils

import android.content.Context
import android.os.Build
import android.view.View

/**
 * dp 2 px
 */
fun Float.toPx(context: Context) = (this * context.resources.displayMetrics.scaledDensity + 0.5F)

/**
 * px 2 dp
 */
fun Float.toDp(context: Context) = (this / context.resources.displayMetrics.scaledDensity + 0.5F)

fun View?.visible() {
    if (this != null) visibility = View.VISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

fun View?.invisible() {
    if (this != null) visibility = View.INVISIBLE
}

fun View?.gone() {
    if (this != null) visibility = View.GONE
}

fun Context.resColor(resId: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.resources.getColor(resId, this.theme)
    } else {
        this.resources.getColor(resId)
    }
}
