package com.qiufg.template.wedget.decoration;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qiufg.template.App;
import com.qiufg.template.R;
import com.qiufg.template.bean.GitHub;

import java.util.List;

/**
 * Created by qiufg on 2019/10/11 12:31.
 * <p>
 * Desc：Note分类
 */
public class NoteDecoration extends RecyclerView.ItemDecoration {

    public static final int SPAN_COUNT = 4;
    private List<GitHub> mGitHubs;
    private final int mDirHeight, mArtHeight;
    private final int mHorizontalGap;
    private final Paint mPaint;

    public NoteDecoration() {
        Resources resources = App.getInstance().getResources();
        mDirHeight = resources.getDimensionPixelSize(R.dimen.note_dir_height);
        mArtHeight = resources.getDimensionPixelSize(R.dimen.note_art_height);
        mHorizontalGap = resources.getDimensionPixelSize(R.dimen.note_item_horizontal_gap);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.note_item_dir_text));
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setColor(resources.getColor(R.color.note_item_dir_text));
    }

    public void setGitHubs(List<GitHub> gitHubs) {
        mGitHubs = gitHubs;
    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mGitHubs == null || mGitHubs.isEmpty()) return;

        final int childCount = state.getItemCount();
        for (int position = 0; position < childCount; position++) {

            final View child = parent.getChildAt(position);
            if (child == null) continue;
            int index = parent.getChildAdapterPosition(child);
            if (index % SPAN_COUNT != 0) continue;

            String dir = mGitHubs.get(index).getPath();
            String currentRow = dir.substring(0, dir.indexOf("/"));
            if (index >= SPAN_COUNT) {
                String path = mGitHubs.get(index - SPAN_COUNT).getPath();
                String previousRow = path.substring(0, path.indexOf("/"));
                if (currentRow.equals(previousRow)) {
                    continue;
                }
            }

            final int left = parent.getPaddingLeft() + 16;
            int y = child.getTop() - mDirHeight / 4;
            c.drawText(currentRow, left, y, mPaint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        String previousRow;
        if (position < SPAN_COUNT) {
            previousRow = null;
        } else {
            String path = mGitHubs.get((position / SPAN_COUNT - 1) * SPAN_COUNT).getPath();
            previousRow = path.substring(0, path.indexOf("/"));
        }
        String path = mGitHubs.get(position / SPAN_COUNT * SPAN_COUNT).getPath();
        String currentRow = path.substring(0, path.indexOf("/"));
        if (currentRow.equals(previousRow)) {
            outRect.top = mArtHeight;
        } else {
            outRect.top = mDirHeight;
        }
        outRect.bottom = (position >= (state.getItemCount() - 1) / SPAN_COUNT * SPAN_COUNT) ? mArtHeight : 0;


        //当有2列的时候， 只有一个分割线。平分到2个item中就是一人1/2。同理 n列时，每个item分割线应为(n-1)/n，即一行中item间隔分割线总数/item数
        int column = (position) % SPAN_COUNT + 1;                             // 计算这个child 处于第几列
        outRect.left = (column - 1) * mHorizontalGap / SPAN_COUNT;            //左侧为(当前条目数-1)/总条目数*divider宽度
        outRect.right = (SPAN_COUNT - column) * mHorizontalGap / SPAN_COUNT;  //右侧为(总条目数-当前条目数)/总条目数*divider宽度
    }
}
