package jdr.tv.shows.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import jdr.tv.local.entities.Show
import jdr.tv.shows.databinding.ItemShowPosterBinding
import jdr.tv.ui.adapter.BaseAdapter
import jdr.tv.ui.adapter.BindingViewHolder

class ShowsAdapter(private val onClick: (Long) -> Unit) : BaseAdapter<Show, ItemShowPosterBinding>(itemCallback) {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BindingViewHolder<Show, ItemShowPosterBinding> {
        return BindingViewHolder(ItemShowPosterBinding.inflate(inflater, parent, false)) {
            show = it
            clickListener = android.view.View.OnClickListener { _ ->
                onClick(it.id)
            }
        }
    }

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Show>() {
            override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
                return oldItem.id == newItem.id && oldItem.posterPath == newItem.posterPath
            }
        }
    }
}
