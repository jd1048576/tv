package jdr.tv.ui.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

class ModelBuilder {

    internal val newList = ArrayList<Model>()

    fun <BINDING : ViewDataBinding> model(@LayoutRes layoutId: Int, id: Long, bind: BINDING.() -> Unit) {
        @Suppress("UNCHECKED_CAST")
        newList.add(Model(layoutId, id, bind as ViewDataBinding.() -> Unit))
    }
}
