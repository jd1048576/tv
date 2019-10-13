package jdr.tvtracker.utils;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.View;

public class SpacingDecoration extends ItemDecoration {
    private final int horizontalSpacing;
    private final int verticalSpacing;

    public SpacingDecoration(int horizontalSpacing, int verticalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            getGridItemOffsets(outRect, position, position % spanCount, spanCount);
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            getGridItemOffsets(outRect, position, ((LayoutParams) view.getLayoutParams()).getSpanIndex(), ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount());
        } else if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
        } else {
            int i;
            if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == 1) {
                i = this.horizontalSpacing;
                outRect.left = i;
                outRect.right = i;
                if (position == 0) {
                    outRect.top = this.verticalSpacing;
                }
                outRect.bottom = this.verticalSpacing;
                return;
            }
            i = this.verticalSpacing;
            outRect.top = i;
            outRect.bottom = i;
            if (position == 0) {
                outRect.left = this.horizontalSpacing;
            }
            outRect.right = this.horizontalSpacing;
        }
    }

    private void getGridItemOffsets(Rect outRect, int position, int column, int spanCount) {
        int i = this.horizontalSpacing;
        outRect.left = ((spanCount - column) * i) / spanCount;
        outRect.right = (i * (column + 1)) / spanCount;
        if (position < spanCount) {
            outRect.top = this.verticalSpacing;
        }
        outRect.bottom = this.verticalSpacing;
    }
}
