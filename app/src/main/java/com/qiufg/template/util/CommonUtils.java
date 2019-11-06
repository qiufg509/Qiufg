package com.qiufg.template.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;

import androidx.annotation.RequiresPermission;
import androidx.core.content.FileProvider;

import com.qiufg.template.App;

import java.io.File;

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

    /**
     * 获取版本名
     *
     * @return 版本名
     */
    public static String getVersionName() {
        try {
            PackageManager packageManager = App.getInstance().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(App.getInstance().getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知版本";
        }
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static long getVersionCode() {
        try {
            PackageManager packageManager = App.getInstance().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(App.getInstance().getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return packInfo.getLongVersionCode();
            } else {
                return packInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = App.getInstance().getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
                        App.getInstance().getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }

    /**
     * 获取请求user-agent
     *
     * @return user-agent
     */
    public static String getUserAgent() {
        String userAgent;
        try {
            userAgent = WebSettings.getDefaultUserAgent(App.getInstance());
        } catch (Exception e) {
            userAgent = System.getProperty("http.agent");
        }
        if (userAgent == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 安装apk
     *
     * @param activity Activity
     * @param apkPath  本地apk路径
     */
    public static void installApk(Activity activity, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(apkPath);
        if (!file.exists() || !file.isFile()) {
            ToastUtils.toast("文件下载失败，请到应用市场更新");
            return;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {//大于7.0使用此方法
            Uri apkUri = FileProvider.getUriForFile(App.getInstance(), App.getInstance().getPackageName() + ".fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {//小于7.0就简单了
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }
}
