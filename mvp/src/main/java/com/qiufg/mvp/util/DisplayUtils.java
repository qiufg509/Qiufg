package com.qiufg.mvp.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.qiufg.mvp.App;

/**
 * Created by fengguang.qiu on 2019/03/25 16:04.
 * <p>
 * Desc：dp、sp 转换为 px 的工具类
 */
public class DisplayUtils {

    private static final String TAG = "DisplayUtils";
    private volatile static boolean mHasCheckAllScreen;
    private volatile static boolean mIsAllScreenDevice;
    @NonNull
    private volatile static Point[] mRealSizes = new Point[2];

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue px
     * @return int dip
     */
    public static int px2dip(float pxValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue dip
     * @return int px
     */
    public static int dip2px(float dipValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue px
     * @return int sp
     */
    public static int px2sp(float pxValue) {
        final float fontScale = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue sp
     * @return int px
     */
    public static int sp2px(float spValue) {
        final float fontScale = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    public static float getDensity() {
        Resources resources = App.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    /**
     * 返回屏幕宽度(px)
     *
     * @return 返回屏幕宽度(px)
     */
    public static int getWindowWidth() {
        return App.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 返回屏幕高度(px)
     *
     * @return 返回屏幕高度(px)
     */
    public static int getWindowHeight() {
        return App.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getFullActivityHeight() {
        if (!isAllScreenDevice()) {
            return getWindowHeight();
        }
        return getScreenRealHeight();
    }

    public static int getScreenRealHeight() {
        int orientation = App.getInstance().getResources().getConfiguration().orientation;
        orientation = orientation == Configuration.ORIENTATION_PORTRAIT ? 0 : 1;

        if (mRealSizes[orientation] == null) {
            WindowManager windowManager = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return getWindowHeight();
            }
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            mRealSizes[orientation] = point;
        }
        return mRealSizes[orientation].y;
    }

    public static boolean isAllScreenDevice() {
        if (mHasCheckAllScreen) {
            return mIsAllScreenDevice;
        }
        mHasCheckAllScreen = true;
        mIsAllScreenDevice = false;
        WindowManager windowManager = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            if (height / width >= 1.97f) {
                mIsAllScreenDevice = true;
            }
        }
        return mIsAllScreenDevice;
    }

    /**
     * 获取Navigation高度
     *
     * @return Navigation高度或者0
     */
    public static int getNavigationHeight() {
        try {
            Resources res = App.getInstance().getResources();
            int navigationHeight = res.getIdentifier("navigation_bar_height", "dimen", "android");
            return res.getDimensionPixelSize(navigationHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取status bar高度
     *
     * @return status bar高度或者0
     */
    public static int getStatusBarHeight() {
        try {
            Resources res = App.getInstance().getResources();
            int statusHeight = res.getIdentifier("status_bar_height", "dimen", "android");
            return res.getDimensionPixelSize(statusHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取system bar高度
     *
     * @return system bar高度或者0
     */
    public static int getSystemBarHeight() {
        try {
            Resources res = App.getInstance().getResources();
            int systemHeight = res.getIdentifier("system_bar_height", "dimen", "android");
            return res.getDimensionPixelSize(systemHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
