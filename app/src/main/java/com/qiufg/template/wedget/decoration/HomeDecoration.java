package com.qiufg.template.wedget.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qiufg.template.util.DisplayUtils;

/**
 * Created by fengguang.qiu on 2019/09/17 09:50.
 * <p>
 * Desc：主页列表间距控制
 */
public class HomeDecoration extends RecyclerView.ItemDecoration {

    public static final int SPAN_COUNT = 2;
    private static final int ITEM_MARGIN_HORIZONTAL = DisplayUtils.dip2px(8);
    private static final int ITEM_MARGIN_VERTICAL = DisplayUtils.dip2px(16);
    private static final int ITEM_GAP = DisplayUtils.dip2px(4);

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
//        int count = parent.getChildCount();//获取子View的个数,但是这个并不是获取RecyclerView所有的item个数,而是当前屏幕可见的item个数
//        int itemCount = state.getItemCount();
        if (position == 0) return;//头部无间隙
        outRect.top = (position <= SPAN_COUNT) ? ITEM_MARGIN_VERTICAL : ITEM_GAP;
//        outRect.bottom = (position > itemCount - 3) ? ITEM_MARGIN_VERTICAL : 0;//加载更多时，底部item和新添加item有不同间距
        outRect.left = (position % SPAN_COUNT == 1) ? ITEM_MARGIN_HORIZONTAL : ITEM_GAP;
        outRect.right = (position % SPAN_COUNT == 0) ? ITEM_MARGIN_HORIZONTAL : 0;

    }
}
