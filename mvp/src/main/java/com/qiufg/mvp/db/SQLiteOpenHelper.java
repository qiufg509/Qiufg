package com.qiufg.mvp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qiufg.mvp.module.main.model.DaoMaster;
import com.qiufg.mvp.module.main.model.GirlsBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by fengguang.qiu on 2019/05/09 14:55.
 * <p>
 * Desc：数据库升级Helper
 */
public class SQLiteOpenHelper extends DaoMaster.OpenHelper {

    public SQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);
        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
        //noinspection unchecked
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, GirlsBeanDao.class);
    }
}
