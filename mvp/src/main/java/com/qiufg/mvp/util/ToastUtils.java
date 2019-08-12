package com.qiufg.mvp.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.qiufg.mvp.App;

/**
 * Created by fengguang.qiu on 2019/08/07 10:43.
 * <p>
 * Desc：自定义Toast
 */
public class ToastUtils {

    private static Toast mToast = null;

    public static void toast(String text) {
        if (TextUtils.isEmpty(text)) return;
        if (mToast == null) mToast = Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();
    }

    public static void toast(String text, int duration) {
        if (TextUtils.isEmpty(text)) return;
        if (mToast == null) mToast = Toast.makeText(App.getInstance(), text, duration);
        mToast.setText(text);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void toast(@StringRes int strId) {
        if (mToast == null) mToast = Toast.makeText(App.getInstance(), strId, Toast.LENGTH_SHORT);
        mToast.setText(strId);
        mToast.show();
    }


    public static void toast(@StringRes int strId, int duration) {
        if (mToast == null) mToast = Toast.makeText(App.getInstance(), strId, Toast.LENGTH_SHORT);
        mToast.setText(strId);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void toastInCenter(String text) {
        if (TextUtils.isEmpty(text)) return;
        if (mToast == null) mToast = Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setText(text);
        mToast.show();
    }

    public static void toastInCenter(String text, int duration) {
        if (TextUtils.isEmpty(text)) return;
        if (mToast == null) mToast = Toast.makeText(App.getInstance(), text, duration);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setText(text);
        mToast.setDuration(duration);
        mToast.show();
    }
}
