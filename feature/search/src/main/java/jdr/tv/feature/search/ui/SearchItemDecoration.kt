package jdr.tv.feature.search.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager is LinearLayoutManager) {
            outRect.setOffsets(parent.getChildAdapterPosition(view), parent.adapter?.itemCount ?: 0)
        } else {
            throw IllegalArgumentException("Invalid Layout Manager ${parent.layoutManager}")
        }
    }

    private fun Rect.setOffsets(position: Int, childCount: Int) {
        if (position == 0) {
            top = spacing
        } else if (position + 1 == childCount) {
            bottom = spacing
        }
    }
}
