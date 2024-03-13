package com.exam.myapp.binding

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter
import me.utils.toPx

object ViewBindingAdapter {
    @BindingAdapter("android:layout_width")
    fun setLayoutWidth(view: View, width: Int) {
        val params = view.layoutParams
        params.width = width
        view.setLayoutParams(params)
    }
    @BindingAdapter(value = ["shapeRadius","shapeSolidColor"])
    fun View.setViewBackground(shapeRadius: Int = 0, shapeSolidColor: Int = Color.TRANSPARENT){
        val drawable = GradientDrawable()
        drawable.cornerRadius = context.toPx(shapeRadius.toFloat())
        drawable.setColor(shapeSolidColor)
        background = drawable
    }

    @BindingAdapter("android:layout_height")
    fun setLayoutHeight(view: View, height: Int) {
        val params = view.layoutParams
        params.height = height
        view.setLayoutParams(params)
    }

    @BindingAdapter("visible")
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    @BindingAdapter("visibleOrGone")
    fun visibleOrGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("selected")
    fun selected(view: View, selected: Boolean) {
        view.isSelected = selected
    }
}
