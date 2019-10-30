package com.qiufg.server;

import android.app.Application;
import android.content.pm.ApplicationInfo;

/**
 * Created by qiufg on 2019/10/30 15:04.
 * <p>
 * Desc：全局Application
 */
public class App extends Application {

    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public boolean isDebug() {
        return (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
