package jdr.tv.ui.extensions

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

val RecyclerView.linearLayoutManager: LinearLayoutManager get() = layoutManager as LinearLayoutManager

fun RecyclerView.save(bundle: Bundle) {
    var position = RecyclerView.NO_POSITION
    var offset = 0

    val layoutManager = linearLayoutManager
    if (layoutManager.childCount > 0) {
        position = layoutManager.findFirstVisibleItemPosition()
        offset = layoutManager.getChildAt(0)?.top ?: 0
    }

    bundle.putInt("LAYOUT_POSITION", position)
    bundle.putInt("LAYOUT_OFFSET", offset)
}

fun RecyclerView.restore(bundle: Bundle?) {
    if (bundle != null) {
        val position = bundle.getInt("LAYOUT_POSITION", 0)
        val offset = bundle.getInt("LAYOUT_OFFSET", 0)
        linearLayoutManager.scrollToPositionWithOffset(position, offset)
    }
}
