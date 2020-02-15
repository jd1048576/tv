package jdr.tv.common.ui.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView

fun SearchView.closeIconVisible(visible: Boolean) {
    val blank = query.isNullOrBlank()
    get(0)?.get(2)?.get(1)?.getChildAt(1)?.visibility = if (visible && !blank) View.VISIBLE else View.INVISIBLE
}

private fun ViewGroup.get(index: Int): LinearLayout? {
    return getChildAt(index) as? LinearLayout
}
