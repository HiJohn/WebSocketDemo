package com.exam.myapp.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingAdapter<T : Any, VB : ViewBinding> :
    RecyclerView.Adapter<BaseBindingHolder<VB>>() {

    val mDataList = arrayListOf<T>()

    fun newData(d: MutableList<T>) {
        if (d.isNotEmpty()) {
            d.clear()
        }
        mDataList.addAll(d)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    abstract fun onInflateBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    abstract fun onBind(holder: BaseBindingHolder<VB>, itemData: T, position: Int)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        return BaseBindingHolder(onInflateBinding(inflater, parent))
    }

    override fun onBindViewHolder(holder: BaseBindingHolder<VB>, position: Int) {
        onBind(holder, mDataList[position], position)
    }


}