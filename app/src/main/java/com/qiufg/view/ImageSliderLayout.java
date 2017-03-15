package com.qiufg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.daimajia.slider.library.SliderLayout;

/**
 * Description
 * Author qiufg
 * Date 2017/3/16 13:35
 */

public class ImageSliderLayout extends SliderLayout {
    private onInterceptTouchEvent mTouchEvent;

    public void setTouchEvent(onInterceptTouchEvent touchEvent) {
        mTouchEvent = touchEvent;
    }

    public ImageSliderLayout(Context context) {
        super(context);
    }

    public ImageSliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSliderLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mTouchEvent.intercept(ev);
        return super.dispatchTouchEvent(ev);
    }

    public interface onInterceptTouchEvent{
        void intercept(MotionEvent ev);
    }
}
