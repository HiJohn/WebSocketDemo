package com.exam.myapp.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import me.utils.gone
import me.utils.visible

/**
 *
 *
 */
object ImageBindingAdapter {


    @BindingAdapter(value = ["imageUrl", "placeHolder", "error"], requireAll = false)
    fun loadImage(view: ImageView, url: String, placeHolder: Drawable, error: Drawable) {
        view.load(url) {
            placeholder(placeHolder)
            error(error)
            crossfade(true)
        }
    }

    @BindingAdapter(value = ["imageRound", "placeHolder", "error"], requireAll = false)
    fun loadRoundImage(view: ImageView, url: String, placeHolder: Drawable, error: Drawable) {
        view.load(url){
            placeholder(placeHolder)
            error(error)
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    /**
     * attention 加载失败view会Gone
     * @param view
     * @param url
     */
    @BindingAdapter(value = ["imageCircle"], requireAll = false)
    fun loadCircleImage(view: ImageView, url: String) {
        view.load(url){
            crossfade(true)
            transformations(CircleCropTransformation())
            listener(onError = { request, result ->
                view.gone()
            }, onSuccess = {request, result ->
                view.visible()
            })
        }
    }
}
