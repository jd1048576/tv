package jdr.tv.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("goneIf")
fun goneIf(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter("visibleIf")
fun visibleIf(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}
