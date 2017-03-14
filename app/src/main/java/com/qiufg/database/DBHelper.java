package com.qiufg.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

import com.qiufg.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author qiufg
 * Date 2017/3/13 9:28
 */

public class DBHelper extends SQLiteOpenHelper {


    private static final String DB_Name = "lyl.db";
    private static final int DB_Version = 1;

    private List<String> v1SQL = new ArrayList<>();
    private SparseArray<List<String>> vSQL = new SparseArray<>();

    {
        v1SQL.add("create table test(id int auto_increment primary key, name varchar(20), age int);");
        v1SQL.add("insert into test values(null,'张三',27), (null,'李四',28), (null,'王五',29);");

        vSQL.append(DB_Version, v1SQL);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int ver = oldVersion + 1; ver <= newVersion; ver++) {
            List<String> verSQL = vSQL.get(ver);
            if (verSQL != null) {
                for (String sql : verSQL) {
                    db.execSQL(sql);
                }
            }
            LogUtils.i("升级到数据库" + ver);
        }
    }
}
