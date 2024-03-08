package com.exam.myapp.binding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {
    @BindingAdapter("android:layout_width")
    fun setLayoutWidth(view: View, width: Int) {
        val params = view.layoutParams
        params.width = width
        view.setLayoutParams(params)
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
