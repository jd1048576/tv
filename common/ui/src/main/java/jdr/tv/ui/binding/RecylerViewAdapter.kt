package jdr.tv.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import jdr.tv.ui.widget.RecyclerView

@BindingAdapter("span")
fun setSpan(view: RecyclerView, span: Int) {
    (view.layoutManager as GridLayoutManager).spanCount = span
}
