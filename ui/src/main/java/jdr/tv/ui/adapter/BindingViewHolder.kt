package jdr.tv.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<T, B : ViewDataBinding>(val binding: B, private val bind: B.(T) -> Unit) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T) {
        bind(binding, item)
        binding.executePendingBindings()
    }
}