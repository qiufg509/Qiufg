package com.qiufg.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;

import com.qiufg.util.LogUtils;

public class BindService extends Service {

    private MyBinder mBinder;

    public BindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new MyBinder();
    }

    public class MyBinder extends Binder {

        public void downLoad() {
            LogUtils.d("downLoad" + "     Process.myPid()=" + Process.myPid());

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
