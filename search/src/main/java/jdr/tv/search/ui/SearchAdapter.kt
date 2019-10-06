package jdr.tv.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import jdr.tv.local.entities.Show
import jdr.tv.search.databinding.ItemShowBackdropBinding
import jdr.tv.ui.adapter.BaseAdapter
import jdr.tv.ui.adapter.BindingViewHolder

class SearchAdapter(private val onChanged: () -> Unit, private val onClick: (Long) -> Unit) :
    BaseAdapter<Show, ItemShowBackdropBinding>(itemCallback) {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BindingViewHolder<Show, ItemShowBackdropBinding> {
        return BindingViewHolder(ItemShowBackdropBinding.inflate(inflater, parent, false)) {
            show = it
            clickListener = View.OnClickListener { _ ->
                onClick(it.id)
            }
        }
    }

    override fun onListChanged() {
        onChanged()
    }

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Show>() {
            override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
                return oldItem.id == newItem.id && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }
}
