package com.qiufg.mvp.util;

import android.app.ActivityManager;
import android.content.Context;

import com.qiufg.mvp.App;

/**
 * Created by fengguang.qiu on 2019/08/07 11:24.
 * <p>
 * Desc：通用工具类
 */
public class CommonUtils {

    public static boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return App.getInstance().getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }

}
