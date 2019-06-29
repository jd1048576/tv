package jdr.tv.search.ui

import androidx.annotation.Keep
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import jdr.tv.base.Dispatchers.IOExecutor
import jdr.tv.local.entities.Show
import jdr.tv.search.R
import jdr.tv.search.databinding.ItemShowBackdropBinding
import jdr.tv.ui.adapter.BindingPagedListAdapter

@Keep
class SearchAdapter(private val requestManager: RequestManager) :
    BindingPagedListAdapter<Show, ItemShowBackdropBinding>(AsyncDifferConfig.Builder(diffUtil).setBackgroundThreadExecutor(IOExecutor).build()) {

    override fun layoutIdForViewType(viewType: Int): Int {
        return R.layout.item_show_backdrop
    }

    override fun bind(binding: ItemShowBackdropBinding, item: Show?) {
        binding.show = item
        binding.requestManager = requestManager
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Show>() {
            override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
