package com.qiufg.util;

import android.util.Log;

import com.qiufg.BuildConfig;

/**
 * LogUtils
 *
 * @author fengguang.qiu
 * @description 日志工具类
 * @date 2015-3-3
 * @modifier victor
 */
public class LogUtils {

    public static int LOG_LEVEL = 6;
    public static final int VERBOSE = 5;
    public static final int DEBUG = 4;
    public static final int INFO = 3;
    public static final int WARN = 2;
    public static final int ERROR = 1;

    static {
        LOG_LEVEL = BuildConfig.DEBUG ? 6 : 0;
    }

    public static void v(String msg) {
        if (LOG_LEVEL > VERBOSE) {
            Log.v(generateTAG(msg), msg);
        }
    }

    public static void d(String msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(generateTAG(msg), msg);
        }
    }

    public static void i(String msg) {
        if (LOG_LEVEL > INFO) {
            Log.i(generateTAG(msg), msg);
        }
    }

    public static void w(String msg) {
        if (LOG_LEVEL > WARN) {
            Log.w(generateTAG(msg), msg);
        }
    }

    public static void e(String msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(generateTAG(msg), msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (LOG_LEVEL > WARN) {
            Log.w(generateTAG(msg), msg, tr);
        }
    }

    private static String generateTAG(String msg) {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
        String fullClassName = stackTrace.getClassName();
        if (null == msg) {
            int lineNumber = stackTrace.getLineNumber();
            throw new IllegalArgumentException(fullClassName + "第 " + lineNumber + " 行方法参数不能为null！");
        }
        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    }
}