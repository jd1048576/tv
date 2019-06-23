package jdr.tv.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object SpacingItemDecoration {

    private fun setGridItemOffsets(outRect: Rect, position: Int, column: Int, spanCount: Int, horizontalSpacing: Int, verticalSpacing: Int) {
        outRect.apply {
            left = (spanCount - column) * horizontalSpacing / spanCount
            right = horizontalSpacing * (column + 1) / spanCount
            if (position < spanCount) {
                top = verticalSpacing
            }
            bottom = verticalSpacing
        }
    }

    class LinearLayout(private val horizontalSpacing: Int, private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
        constructor(spacing: Int) : this(spacing, spacing)

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val layoutManager = parent.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val position = parent.getChildAdapterPosition(view)
                outRect.apply {
                    if (layoutManager.orientation == RecyclerView.VERTICAL) {
                        if (position == 0) {
                            top = verticalSpacing
                        }
                        bottom = verticalSpacing
                        left = horizontalSpacing
                        right = horizontalSpacing
                    } else {
                        top = verticalSpacing
                        bottom = verticalSpacing
                        if (position == 0) {
                            left = horizontalSpacing
                        }
                        right = horizontalSpacing
                    }
                }
            } else {
                throw IllegalArgumentException("Invalid Layout Manager $layoutManager")
            }
        }
    }

    class GridLayout(private val horizontalSpacing: Int, private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
        constructor(spacing: Int) : this(spacing, spacing)

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val layoutManager = parent.layoutManager
            if (layoutManager is GridLayoutManager) {
                val position = parent.getChildAdapterPosition(view)
                val spanCount = layoutManager.spanCount
                setGridItemOffsets(outRect, position, position % spanCount, spanCount, horizontalSpacing, verticalSpacing)
            } else {
                throw IllegalArgumentException("Invalid Layout Manager $layoutManager")
            }
        }
    }

    class StaggeredGridLayout(private val horizontalSpacing: Int, private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
        constructor(spacing: Int) : this(spacing, spacing)

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val layoutManager = parent.layoutManager
            if (layoutManager is StaggeredGridLayoutManager) {
                val position = parent.getChildAdapterPosition(view)
                val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                setGridItemOffsets(outRect, position, layoutParams.spanIndex, layoutManager.spanCount, horizontalSpacing, verticalSpacing)
            } else {
                throw IllegalArgumentException("Invalid Layout Manager $layoutManager")
            }
        }
    }
}