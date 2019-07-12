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

    abstract fun bindingForViewType(viewType: Int, inflater: LayoutInflater, parent: ViewGroup): Binding

    abstract fun bind(binding: Binding, item: T?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<Binding> {
        return BindingViewHolder(bindingForViewType(viewType, LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<Binding>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }
}

class BindingViewHolder<Binding : ViewDataBinding>(internal val binding: Binding) : RecyclerView.ViewHolder(binding.root)