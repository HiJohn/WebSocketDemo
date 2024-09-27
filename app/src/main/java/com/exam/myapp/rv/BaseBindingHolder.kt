package com.exam.myapp.rv

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseBindingHolder<B : ViewBinding>(private val binding: B) : RecyclerView.ViewHolder(binding.root) {

    val b = binding

}
