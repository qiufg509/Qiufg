package com.qiufg.template.bus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.qiufg.server.IBinderPool;
import com.qiufg.template.listener.OnServiceConnectionListener;
import com.qiufg.template.util.Logger;

/**
 * Created by qiufg on 2019/10/30 14:17.
 * <p>
 * Desc：远程服务连接
 */
public class BinderPool {
    private static final String TAG = "BinderPoolImpl";

    public static final int BINDER_CONVERSION = 0;
    public static final int BINDER_SORT = 1;
    public static final int BINDER_SECURITY_CENTER = 2;
    public static final int BINDER_EMAIL_MANAGER = 3;
    private IBinderPool mIBinderPool;
    private Context mContext;
    private OnServiceConnectionListener mOnServiceConnectionListener;
    private static volatile BinderPool mBinderPool;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getBinderPool(Context context) {
        if (mBinderPool == null) {
            synchronized (BinderPool.class) {
                if (mBinderPool == null) {
                    mBinderPool = new BinderPool(context);
                }
            }
        }
        return mBinderPool;
    }

    public void setOnServiceConnectionListener(OnServiceConnectionListener onServiceConnectionListener) {
        mOnServiceConnectionListener = onServiceConnectionListener;
    }

    /**
     * 链接 BinderService
     */
    private synchronized void connectBinderPoolService() {
        Intent intent = new Intent();
        intent.setAction("com.qiufg.remote");
        intent.setPackage("com.qiufg.server");
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * query binder by binderCode from binder pool
     *
     * @param binderCode the unique token of binder
     * @return binder who's token is binderCode<br>
     * return null when not found or BinderPoolService died.
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mIBinderPool != null) {
                binder = mIBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // ignored.
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mIBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (mOnServiceConnectionListener != null) {
                mOnServiceConnectionListener.onConnected();
            }
        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Logger.w(TAG, "binder died.");
            mIBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mIBinderPool = null;
            connectBinderPoolService();
        }
    };
}
