package com.qiufg.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by qiufg on 2019/10/30 13:58.
 * <p>
 * Desc：BinderPool服务
 */
public class BinderPoolService extends Service {

    // 标记当前服务是否销毁（自带异步锁）
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    @Override
    public IBinder onBind(Intent intent) {
        return new BinderPoolImpl(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroyed.set(true);
    }
}