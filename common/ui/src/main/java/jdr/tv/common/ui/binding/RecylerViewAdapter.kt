package jdr.tv.common.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import jdr.tv.common.ui.widget.RecyclerView

@BindingAdapter("span")
fun setSpan(view: RecyclerView, span: Int) {
    (view.layoutManager as GridLayoutManager).spanCount = span
}
