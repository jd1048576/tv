package jdr.tv.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import jdr.tv.base.extensions.toFormattedString
import jdr.tv.details.databinding.ItemShowDetailsOverviewBinding
import jdr.tv.local.entities.DetailedShow
import jdr.tv.ui.adapter.BaseAdapter
import jdr.tv.ui.adapter.BindingViewHolder

class ShowDetailsAdapter : BaseAdapter<DetailedShow, ViewDataBinding>(itemCallback) {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BindingViewHolder<DetailedShow, ViewDataBinding> {
        return BindingViewHolder(ItemShowDetailsOverviewBinding.inflate(inflater, parent, false)) {
            with(this as ItemShowDetailsOverviewBinding) {
                overview = it.show.overview.takeUnless { it.isEmpty() }
                updated = "Last Updated ${it.details.detailsUpdatedAt.toFormattedString()}"
            }
        }
    }

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<DetailedShow>() {
            override fun areItemsTheSame(oldItem: DetailedShow, newItem: DetailedShow): Boolean {
                return oldItem.show.id == newItem.show.id
            }

            override fun areContentsTheSame(oldItem: DetailedShow, newItem: DetailedShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}
