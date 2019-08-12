package com.qiufg.mvp;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import com.fm.openinstall.OpenInstall;
import com.qiufg.mvp.util.CommonUtils;
import com.qiufg.mvp.util.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by fengguang.qiu on 2019/08/07 10:43.
 * <p>
 * Desc：Application
 */
public class App extends Application {

    private static App INSTANCE;
    private RefWatcher mRefWatcher;
    private int activityForegroundCount;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        mRefWatcher = LeakCanary.install(this);
        if (CommonUtils.isMainProcess()) {
            OpenInstall.init(this);
            registerLifecycle();
        }
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public boolean isDebug() {
        return (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    private void registerLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activityForegroundCount == 0) {
                    Logger.i("应用回到前台");
                }
                activityForegroundCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityForegroundCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}
