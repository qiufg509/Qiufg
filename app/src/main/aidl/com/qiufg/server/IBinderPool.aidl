// IBinderPool.aidl
package com.qiufg.server;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int code);
}
