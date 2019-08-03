package jdr.tv.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import jdr.tv.base.Dispatchers.IOExecutor

abstract class BaseAdapter : RecyclerView.Adapter<ModelViewHolder>() {

    private val differCallback = DifferCallback()
    private val differ = AsyncListDiffer(differCallback, asyncDifferConfig)

    private val currentList: List<Model>
        get() = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        return ModelViewHolder.from(parent, viewType)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        currentList[position].bind(holder.binding)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].layoutId
    }

    fun requestModelBuild() {
        val previousItemCount = itemCount
        val modelBuilder = ModelBuilder()

        buildModels(modelBuilder)

        differCallback.listChanged = false
        differ.submitList(modelBuilder.newList) { if (previousItemCount != 0 && differCallback.listChanged) onListChanged() }
    }

    abstract fun buildModels(modelBuilder: ModelBuilder)

    open fun onListChanged() {
    }

    private inner class DifferCallback : ListUpdateCallback {

        var listChanged: Boolean = false

        override fun onInserted(position: Int, count: Int) {
            listChanged = true
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            listChanged = true
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            listChanged = true
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            listChanged = true
            notifyItemRangeChanged(position, count, payload)
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<Model>() {
            override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
                return true
            }
        }
        private val asyncDifferConfig = AsyncDifferConfig.Builder(diff).setBackgroundThreadExecutor(IOExecutor).build()
    }
}
