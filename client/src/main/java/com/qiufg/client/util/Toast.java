package com.qiufg.client.util;

import android.content.Context;
import android.view.Gravity;

/**
 * Description 多次Toast不重叠
 * Author qiufg
 * Date 2017/3/14 16:23
 */

public class Toast {

    private static android.widget.Toast sToast;

    public static void show(Context mContext, String message) {
        if (sToast == null) {
            sToast = android.widget.Toast.makeText(mContext, message, android.widget.Toast.LENGTH_LONG);
        } else {
            sToast.setText(message);
        }
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }
}
