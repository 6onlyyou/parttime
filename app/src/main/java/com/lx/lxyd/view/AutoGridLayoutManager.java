package com.lx.lxyd.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


public class AutoGridLayoutManager extends GridLayoutManager {

    private int measuredWidth = 0;
    private int measuredHeight = 0;
    // 总页数
    private int totalPages;

    public AutoGridLayoutManager(Context context, AttributeSet attrs,
                                 int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public AutoGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public AutoGridLayoutManager(Context context, int spanCount,
                                 int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler,
                          RecyclerView.State state, int widthSpec, int heightSpec) {
        int count = state.getItemCount();
        if (count > 0) {


        if (measuredHeight <= 0) {
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                measureChild(view, widthSpec, heightSpec);
                measuredWidth = View.MeasureSpec.getSize(widthSpec);
                measuredHeight = view.getMeasuredHeight() * getSpanCount();
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
        } else {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //如果页面总数大于 1 则可以滑动
        if (totalPages > 1) {
            return super.scrollHorizontallyBy(dx, recycler, state);
        } else {
            return 0;
        }
    }
}