package jdr.tv.search.ui

import android.view.View
import com.bumptech.glide.RequestManager
import jdr.tv.local.entities.Show
import jdr.tv.search.R
import jdr.tv.search.databinding.ItemShowBackdropBinding
import jdr.tv.ui.adapter.ModelBuilder
import jdr.tv.ui.adapter.TypedAdapter

class SearchAdapter(private val requestManager: RequestManager, private val onCurrentListChanged: () -> Unit, private val onClick: (Long) -> Unit) :
    TypedAdapter<List<Show>>() {

    override fun buildModels(modelBuilder: ModelBuilder, data: List<Show>) = with(modelBuilder) {
        data.forEach {
            model<ItemShowBackdropBinding>(R.layout.item_show_backdrop, it.id) {
                show = it
                requestManager = this@SearchAdapter.requestManager
                clickListener = View.OnClickListener { _ ->
                    onClick(it.id)
                }
            }
        }
    }

    override fun onListChanged() {
        onCurrentListChanged()
    }
}
