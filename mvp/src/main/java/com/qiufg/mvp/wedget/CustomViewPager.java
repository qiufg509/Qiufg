package com.qiufg.mvp.wedget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by fengguang.qiu on 2019/08/30 11:03.
 * <p>
 * Desc：可以禁用滑动功能的ViewPager
 */
public class CustomViewPager extends ViewPager {

    private boolean isScroll = true;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets scroll. true 可以滑动  false 不可以滑动
     *
     * @param isScroll the is scroll
     */
    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (MotionEvent.ACTION_UP == ev.getAction()) performClick();
        return isScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        return isScroll && super.executeKeyEvent(event);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }
}
