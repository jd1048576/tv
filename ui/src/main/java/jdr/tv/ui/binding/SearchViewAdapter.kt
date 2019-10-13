package jdr.tv.ui.binding

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter

@BindingAdapter("closeIconVisible")
fun closeIconVisible(view: SearchView, visible: Boolean) {
    view.get(0)?.get(2)?.get(1)?.getChildAt(1)?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

private fun ViewGroup.get(index: Int): LinearLayout? {
    return getChildAt(index) as? LinearLayout
}
