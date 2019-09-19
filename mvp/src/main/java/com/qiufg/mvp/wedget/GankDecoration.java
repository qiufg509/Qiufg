package com.qiufg.mvp.wedget;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qiufg.mvp.App;
import com.qiufg.mvp.util.DisplayUtils;

/**
 * Created by fengguang.qiu on 2019/09/19 10:58.
 * <p>
 * Desc：分类页gank列表分割线
 */
public class GankDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private static final int LINE_SIZE = DisplayUtils.dip2px(1);
    private final Drawable mDivider;

    public GankDecoration() {
        //获取系统的divider
        final TypedArray a = App.getInstance().obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position > 0) {
            outRect.top = LINE_SIZE;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //左边缘距离RecyclerView左边的距离
        final int left = parent.getPaddingLeft();
        //右边缘距离RecyclerView右边边的距离
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom;
            //去掉最后一条的分割线
            if (i == childCount - 1) {//bottom和top相等，即高度为0 不显示
                bottom = top;
            } else {
                bottom = top + mDivider.getIntrinsicHeight();
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
