package jdr.tv.feature.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import jdr.tv.common.ui.adapter.BaseAdapter
import jdr.tv.common.ui.adapter.BindingViewHolder
import jdr.tv.data.local.entities.Show
import jdr.tv.feature.search.databinding.ItemSearchBinding

class SearchAdapter(private val onChanged: () -> Unit, private val onClick: (Long) -> Unit) :
    BaseAdapter<Show, ItemSearchBinding>(itemCallback) {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BindingViewHolder<Show, ItemSearchBinding> {
        return BindingViewHolder(ItemSearchBinding.inflate(inflater, parent, false)) {
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
