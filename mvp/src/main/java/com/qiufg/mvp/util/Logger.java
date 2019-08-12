package com.qiufg.mvp.util;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.qiufg.mvp.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fengguang.qiu on 2019/08/07 10:43.
 * <p>
 * Desc：日志工具类
 */
public class Logger {

    private static int LOG_LEVEL = 6;
    private static final int VERBOSE = 5;
    private static final int DEBUG = 4;
    private static final int INFO = 3;
    private static final int WARN = 2;
    private static final int ERROR = 1;
    private static final int NONE = 0;
    private static final SimpleDateFormat formatName = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
    private static final SimpleDateFormat formatContent = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINESE);

    static {
        LOG_LEVEL = App.getInstance().isDebug() ? LOG_LEVEL : NONE;
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

    public static void v(String tag, String msg) {
        if (LOG_LEVEL >= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_LEVEL >= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_LEVEL >= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_LEVEL >= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_LEVEL >= ERROR) {
            Log.e(tag, msg);
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

    public static void file(@NonNull String msg) {
        Date date = new Date();
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
        int lineNumber = stackTrace.getLineNumber();
        FileUtils.output2File(formatContent.format(date) + "\t"
                        + generateTAG(msg) + "\t"
                        + lineNumber + "\t"
                        + msg + "\n",
                App.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                        + File.separator
                        + "logs", formatName.format(date) + ".txt");
    }

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            i(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            i(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            i(tag, "║ " + line);
        }
        printLine(tag, false);
    }
}