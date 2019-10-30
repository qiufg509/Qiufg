package com.qiufg.server.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.qiufg.server.IBinderPool;

import java.lang.ref.SoftReference;

/**
 * Created by qiufg on 2019/10/30 14:38.
 * <p>
 * Desc：Binder总线
 */
public class BinderPoolImpl extends IBinderPool.Stub {

    private static final int BINDER_CONVERSION = 0;
    private static final int BINDER_SORT = 1;
    private static final int BINDER_SECURITY_CENTER = 2;
    private static final int BINDER_EMAIL_MANAGER = 3;

    private SoftReference<Context> mReference;

    BinderPoolImpl(Context context) {
        mReference = new SoftReference<>(context);
    }

    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (mReference == null) {
            return false;
        }
        PackageManager packageManager = mReference.get().getPackageManager();
        String[] packages = packageManager.getPackagesForUid(getCallingUid());
        if (packages == null || packages.length == 0) {
            return false;
        }
        boolean checkPermission = PackageManager.PERMISSION_GRANTED ==
                packageManager.checkPermission("com.qiufg.permission.SERVER", packages[0]);
        return checkPermission && super.onTransact(code, data, reply, flags);
    }

    @Override
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        switch (binderCode) {
            case BINDER_CONVERSION: {
                binder = new ConversionImpl();
                break;
            }
            case BINDER_SORT: {
                binder = new SortImpl();
                break;
            }
            case BINDER_SECURITY_CENTER: {
                binder = new SecurityCenterImpl();
                break;
            }
            case BINDER_EMAIL_MANAGER: {
                binder = new EmailManagerImpl();
                break;
            }
        }
        return binder;
    }
}
