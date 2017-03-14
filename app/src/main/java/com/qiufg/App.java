package com.qiufg;

import android.app.Application;

/**
 * Author qiufg
 * Date 2017/2/19
 */

public class App extends Application {

    private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
