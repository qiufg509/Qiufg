package com.qiufg;

/**
 * 单例设计模式
 * <p>
 * Author qiufg
 * Date 2017/3/13
 */

public class Singleton {

    private Singleton() {
    }


    /**
     * 懒汉式
     */
    private static Singleton mSingleton;

    public static Singleton newInstance() {
        if (mSingleton == null) {
            synchronized (Singleton.class) {
                if (mSingleton == null) {
                    mSingleton = new Singleton();
                }
            }
        }
        return mSingleton;
    }

    /**
     * 静态内部类
     */
    private static class SingletonHolder {
        private static Singleton sSingleton = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.sSingleton;
    }
}
