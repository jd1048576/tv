package jdr.tv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.asExecutor

abstract class BaseAdapter<T, B : ViewDataBinding>(itemCallback: DiffUtil.ItemCallback<T>) : RecyclerView.Adapter<BindingViewHolder<T, B>>() {

    private val differCallback = DifferCallback()
    private val differ = AsyncListDiffer(differCallback, config(itemCallback))

    val currentList: List<T>
        get() = differ.currentList

    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BindingViewHolder<T, B>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T, B> {
        return onCreateViewHolder(LayoutInflater.from(parent.context), parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T, B>, position: Int) {
        currentList[position]?.also { holder.bind(it) }
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder<T, B>) {
        holder.binding.unbind()
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun submitList(list: List<T>) {
        val previousItemCount = itemCount
        differCallback.listChanged = false
        differ.submitList(list) { if (previousItemCount != 0 && differCallback.listChanged) onListChanged() }
    }

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
        private fun <T> config(itemCallback: DiffUtil.ItemCallback<T>): AsyncDifferConfig<T> {
            return AsyncDifferConfig.Builder(itemCallback).setBackgroundThreadExecutor(IO.asExecutor()).build()
        }
    }
}
