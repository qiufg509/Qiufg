package com.qiufg.template;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import com.fm.openinstall.OpenInstall;
import com.qiufg.template.bus.CustomSkinLoader;
import com.qiufg.template.util.CommonUtils;
import com.qiufg.template.util.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

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

        SkinCompatManager.withoutActivity(this)
                .addStrategy(new CustomSkinLoader())
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
//                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
//                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
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

    public static RefWatcher getRefWatcher() {
        return INSTANCE.mRefWatcher;
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
