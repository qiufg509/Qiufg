package com.qiufg.mvp.util;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.RequiresPermission;

import com.qiufg.mvp.App;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

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

    /**
     * 判断网络功能是否可用
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable() {
        NetworkInfo info = ((ConnectivityManager) App.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}
