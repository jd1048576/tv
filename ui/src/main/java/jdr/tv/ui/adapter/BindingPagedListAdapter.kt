package jdr.tv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.RecyclerView

abstract class BindingPagedListAdapter<T, Binding : ViewDataBinding>(config: AsyncDifferConfig<T>) :
    PagedListAdapter<T, BindingViewHolder<Binding>>(config) {

    abstract fun layoutIdForViewType(viewType: Int): Int

    abstract fun bind(binding: Binding, item: T?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<Binding> {
        return BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutIdForViewType(viewType), parent, false))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<Binding>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }
}

class BindingViewHolder<Binding : ViewDataBinding>(internal val binding: Binding) : RecyclerView.ViewHolder(binding.root)