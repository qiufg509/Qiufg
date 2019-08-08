package com.qiufg.mvp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qiufg.mvp.base.App;
import com.qiufg.mvp.module.main.m.DaoMaster;
import com.qiufg.mvp.module.main.m.DaoSession;
import com.qiufg.mvp.module.main.m.UserInfo;
import com.qiufg.mvp.module.main.m.UserInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.ref.SoftReference;
import java.util.List;

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

    /**
     * 插入一条记录
     *
     * @param userInfo 用户信息
     */
    public void insertUserInfo(UserInfo userInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        userInfoDao.insertOrReplace(userInfo);
    }

    /**
     * 插入用户集合
     *
     * @param userInfos 用户信息List集合
     */
    public void insertUserInfo(List<UserInfo> userInfos) {
        if (userInfos == null || userInfos.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        userInfoDao.insertInTx(userInfos);
    }

    /**
     * 删除一条记录
     *
     * @param userInfo 用户信息
     */
    public void deleteUserInfo(UserInfo userInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        userInfoDao.delete(userInfo);
    }

    /**
     * 更新一条记录
     *
     * @param userInfo 用户信息
     */
    public void updateUserInfo(UserInfo userInfo) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        userInfoDao.update(userInfo);
    }

    /**
     * 查询所有用户
     *
     * @return 用户信息集合
     */
    public List<UserInfo> queryUserInfos() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        QueryBuilder<UserInfo> qb = userInfoDao.queryBuilder();
        return qb.list();
    }

    /**
     * 查询用户列表
     *
     * @param gender 性别
     * @return 用户信息集合
     */
    public List<UserInfo> queryUserInfos(int gender) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        QueryBuilder<UserInfo> qb = userInfoDao.queryBuilder();
        qb.where(UserInfoDao.Properties.Gender.eq(gender)).orderAsc(UserInfoDao.Properties.Age);
        return qb.list();
    }
}
