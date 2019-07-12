package jdr.tv.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import jdr.tv.base.Dispatchers.IOExecutor
import jdr.tv.local.entities.Show
import jdr.tv.search.databinding.ItemShowBackdropBinding
import jdr.tv.ui.adapter.BindingPagedListAdapter

class SearchAdapter(private val requestManager: RequestManager, private val onCurrentListChanged: () -> Unit, private val onClick: (Long) -> Unit) :
    BindingPagedListAdapter<Show, ItemShowBackdropBinding>(AsyncDifferConfig.Builder(diffUtil).setBackgroundThreadExecutor(IOExecutor).build()) {

    override fun bindingForViewType(viewType: Int, inflater: LayoutInflater, parent: ViewGroup): ItemShowBackdropBinding {
        return ItemShowBackdropBinding.inflate(inflater, parent, false)
    }

    override fun bind(binding: ItemShowBackdropBinding, item: Show?) {
        binding.show = item
        binding.requestManager = requestManager
        if (item != null) {
            binding.clickListener = View.OnClickListener {
                onClick(item.id)
            }
        }
    }

    override fun onCurrentListChanged(previousList: PagedList<Show>?, currentList: PagedList<Show>?) {
        super.onCurrentListChanged(previousList, currentList)
        if (currentList?.size == currentList?.config?.pageSize) onCurrentListChanged()
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
