package me.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

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



fun <T : Parcelable> Intent.parcelableCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableExtra(key, clazz)
    } else {
        this.getParcelableExtra<T>(key)
    }
}

fun <T : Parcelable> Bundle.parcelableCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, clazz)
    } else {
        this.getParcelable<T>(key)
    }
}

fun TextView.setClickableSpan(text: String, clickableText: String, @ColorRes color:Int, clickListener: View.OnClickListener) {
    val spannableString = SpannableString(text)

    // 设置自定义颜色
    val colorSpan = ForegroundColorSpan(context.resColor(color))
    spannableString.setSpan(colorSpan, text.indexOf(clickableText), text.indexOf(clickableText) + clickableText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // 添加点击事件
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            clickListener.onClick(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
        }
    }
    spannableString.setSpan(clickableSpan, text.indexOf(clickableText), text.indexOf(clickableText) + clickableText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // 设置TextView的文本和点击事件
    this.text = spannableString
    this.movementMethod = LinkMovementMethod.getInstance()

}

fun Context.compatColor(@ColorRes color:Int):Int{
    return ResourcesCompat.getColor(resources,color,theme)
}
fun Context.getScreenSize(): Pair<Int, Int> {
    val displayMetrics = resources.displayMetrics
    return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
fun View.clickNoRepeat(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action(it)
    }
}