package com.qiufg.template.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qiufg.template.App;
import com.qiufg.template.bean.DaoMaster;

import java.lang.ref.SoftReference;

/**
 * Created by fengguang.qiu on 2019/03/22 15:15.
 * <p>
 * Desc：数据库操作类
 */
public class DBManager {
    private final static String dbName = "qiufg.db";
    private static DBManager mInstance;
    private SQLiteOpenHelper mOpenHelper;
    private SoftReference<Context> mReference;

    private DBManager() {
        mReference = new SoftReference<>(App.getInstance());
        mOpenHelper = new SQLiteOpenHelper(mReference.get(), dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @return DBManager
     */
    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mOpenHelper == null && mReference.get() != null) {
            mOpenHelper = new SQLiteOpenHelper(mReference.get(), dbName, null);
        }
        return mOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mOpenHelper == null && mReference.get() != null) {
            mOpenHelper = new SQLiteOpenHelper(mReference.get(), dbName, null);
        }
        return mOpenHelper.getWritableDatabase();
    }

    /**
     * 清空数据库
     */
    public void clearAll() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoMaster.dropAllTables(daoMaster.getDatabase(), true);
        DaoMaster.createAllTables(daoMaster.getDatabase(), true);
    }
}
