package com.example.dllo.mirror_20;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dllo on 16/6/21.
 *
 * 控制recycleView的间距
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int itemCount = mAdapter.getItemCount();
//        int pos = parent.getChildAdapterPosition(view);


        outRect.left = 0;
        outRect.top = 0;
        outRect.bottom = 0;
        outRect.right = space;

//        if (pos != (itemCount -1)) {
//            outRect.right = space;
//        } else {
//            outRect.right = 0;
//        }
    }
}
