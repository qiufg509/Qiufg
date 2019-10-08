package com.qiufg.template.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.qiufg.template.App;
import com.qiufg.template.Const;

/**
 * Created by fengguang.qiu on 2019/03/22 10:04.
 * <p>
 * Desc：SharedPreferences工具
 */
public class SPUtils {

    private volatile static SharedPreferences mSP;

    private SPUtils() {
    }

    /**
     * @param key   键名
     * @param value a string value to share preference file
     */
    public static void putString(String key, String value) {
        getSharePreferences().edit().putString(key, value).apply();
    }

    /**
     * @param key          键名
     * @param defaultValue a string value from share preference file, ask a default value
     */
    public static String getString(String key, String defaultValue) {
        return getSharePreferences().getString(key, defaultValue);
    }

    /**
     * @param key 键名
     * @return string value that store in shared preferences ,default is ""
     */
    public static String getString(String key) {
        return getSharePreferences().getString(key, "");
    }

    /**
     * @param key   键名
     * @param value a boolean value to share preference file
     */
    public static void putBoolean(String key, boolean value) {
        getSharePreferences().edit().putBoolean(key, value).apply();
    }

    /**
     * @param key          键名
     * @param defaultValue a boolean value from share preference file, ask a default
     *                     value
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSharePreferences().getBoolean(key, defaultValue);
    }

    /**
     * @param key   键名
     * @param value a long value to share preference file
     */
    public static void putLong(String key, long value) {
        getSharePreferences().edit().putLong(key, value).apply();
    }

    /**
     * @param key          键名
     * @param defaultValue a long value from share preference file, ask a default value
     */
    public static long getLong(String key, long defaultValue) {
        return getSharePreferences().getLong(key, defaultValue);
    }

    /**
     * @param key 键名
     * @return boolean value that store in shared preferences ,default is false
     */
    public static boolean getBoolean(String key) {
        return getSharePreferences().getBoolean(key, false);
    }

    /**
     * @param key   键名
     * @param value a int value to share preference file
     */
    public static void putInt(String key, int value) {
        getSharePreferences().edit().putInt(key, value).apply();
    }

    /**
     * @param key          键名
     * @param defaultValue a int value from share preference file, ask a default value
     */
    public static int getInt(String key, int defaultValue) {
        return getSharePreferences().getInt(key, defaultValue);
    }

    private static SharedPreferences getSharePreferences() {
        if (mSP == null) {
            synchronized (SPUtils.class) {
                if (mSP == null) {
                    mSP = App.getInstance().getSharedPreferences(Const.SP_NAME_PRIVATE, Context.MODE_PRIVATE);
                }
            }
        }
        return mSP;
    }

    public static void refresh() {
        mSP = null;
    }

    /**
     * 清空配置信息
     */
    public static void clear() {
        mSP.edit().clear().apply();
    }

    /**
     * 清空用户信息
     */
    public static void clearUser() {
        SharedPreferences.Editor edit = mSP.edit();
        edit.remove("uid");
        edit.remove("token");
        edit.apply();
    }

    // =============================================================
    // ------------------------保存数据--------------------------------
    // ------------------ key===>键名 value===>保存的数据值 ----------------
    // =============================================================

    /**
     * 保存数据 根据数据类型自动拆箱
     *
     * @param key 键名
     * @param obj Object类型数据 但仅限于(String/int/float/boolean/long)
     */
    public static void save(String key, Object obj) {
        SharedPreferences.Editor editor = getSharePreferences().edit();
        if (obj instanceof String)
            editor.putString(key, (String) obj);

        else if (obj instanceof Integer)
            editor.putInt(key, (Integer) obj);

        else if (obj instanceof Long)
            editor.putLong(key, (Long) obj);

        else if (obj instanceof Boolean)
            editor.putBoolean(key, (Boolean) obj);

        else if (obj instanceof Float)
            editor.putFloat(key, (Float) obj);

        editor.apply();
    }

    // =============================================================
    // ------------------------获取数据--------------------------------
    // ------ key===>键名 defaultValue===>根据key查找不到的默认数据的数据值 -------
    // =============================================================

    /**
     * 获取Object类型数据 根据接收类型自动拆箱
     *
     * @param key          键名
     * @param defaultValue 根据key获取不到是默认值仅限于(String/int/float/boolean/long)
     */
    public static Object get(String key, Object defaultValue) {
        SharedPreferences sp = getSharePreferences();
        if (defaultValue instanceof String)
            return sp.getString(key, (String) defaultValue);

        else if (defaultValue instanceof Integer)
            return sp.getInt(key, (Integer) defaultValue);

        else if (defaultValue instanceof Long)
            return sp.getLong(key, (Long) defaultValue);

        else if (defaultValue instanceof Boolean)
            return sp.getBoolean(key, (Boolean) defaultValue);

        else if (defaultValue instanceof Float)
            return sp.getFloat(key, (Float) defaultValue);

        return null;
    }
}
