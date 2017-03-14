package com.qiufg.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qiufg.util.LogUtils;

public class StartService extends Service {
    public StartService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d("onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
    }
}
