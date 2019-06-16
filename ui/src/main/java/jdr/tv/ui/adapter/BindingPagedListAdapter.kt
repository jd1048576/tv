package jdr.tv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.RecyclerView

abstract class BindingPagedListAdapter<T>(config: AsyncDifferConfig<T>) : PagedListAdapter<T, BindingViewHolder<T>>(config) {

    @LayoutRes
    abstract fun layoutIdForViewType(viewType: Int): Int

    abstract fun variableIdForViewType(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutIdForViewType(viewType), parent, false)
        return BindingViewHolder(variableIdForViewType(viewType), viewDataBinding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }
}

class BindingViewHolder<T>(private val variableId: Int, private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T?) {
        binding.setVariable(variableId, item)
        binding.executePendingBindings()
    }
}