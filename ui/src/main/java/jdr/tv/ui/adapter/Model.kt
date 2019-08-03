package jdr.tv.ui.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

class Model(@LayoutRes val layoutId: Int, val id: Long, val bind: ViewDataBinding.() -> Unit)
