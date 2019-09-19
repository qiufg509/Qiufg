package com.qiufg.mvp.exception;

import android.app.Activity;
import android.content.Context;
import android.net.ParseException;
import android.os.Looper;

import com.google.gson.JsonParseException;
import com.qiufg.mvp.db.DBManager;
import com.qiufg.mvp.util.Logger;
import com.qiufg.mvp.util.SPUtils;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * Created by fengguang.qiu on 2019/3/19 19:40.
 * <p>
 * Desc：错误动作
 */
public class ErrorAction implements Consumer<Throwable> {

    private static final String TAG = "ErrorAction";
    public WeakReference<? extends Context> mReference;

    public ErrorAction() {
    }

    public ErrorAction(Context context) {
        mReference = new WeakReference<>(context);
    }

    @Override
    public void accept(Throwable throwable) {

        QiufgException exception;
        String message = throwable.getMessage() == null ? throwable.toString() : throwable.getMessage();
        if (throwable instanceof SocketTimeoutException) {
            exception = new QiufgException(QiufgCode.CODE_TIME_OUT);
        } else if (throwable instanceof HttpException) {
            exception = new QiufgException(QiufgCode.CODE_HTTP_ERROR);
        } else if (throwable instanceof ConnectException
                || throwable instanceof UnknownHostException
                || throwable instanceof NoRouteToHostException) {
            exception = new QiufgException(QiufgCode.CODE_CONNECT_FAIL);
        } else if (throwable instanceof JsonParseException
                || throwable instanceof ParseException
                || throwable instanceof JSONException) {
            exception = new QiufgException(QiufgCode.CODE_PARSE_ERROR);
        } else if (throwable instanceof UnknownServiceException) {
            exception = new QiufgException(QiufgCode.CODE_SERVER_ERROR);
        } else if (throwable instanceof SSLHandshakeException) {
            exception = new QiufgException(QiufgCode.CODE_SECURITY_ERROR);
        } else if (throwable instanceof QiufgException) {
            exception = (QiufgException) throwable;
            //登录信息失效，跳转登录页面
            switch (exception.getErrorCode()) {
                case QiufgCode.CODE_LOGIN_INVALID:
                    Logger.e(TAG, "doNext: 清理数据 退出到登录界面");
                    DBManager.getInstance().clearAll();
                    SPUtils.clearUser();
                    if (Looper.myLooper() == Looper.getMainLooper()) {
//                        Intent dialogIntent = new Intent(App.newInstance(), LoginActivity.class);
//                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        App.newInstance().startActivity(dialogIntent);
                    } else if (mReference != null && mReference.get() != null && mReference.get() instanceof Activity) {
                        ((Activity) mReference.get()).runOnUiThread(() -> {
//                            Intent dialogIntent = new Intent(App.newInstance(), LoginActivity.class);
//                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            App.newInstance().startActivity(dialogIntent);
                        });
                    }
                    break;
            }
        } else {
            exception = new QiufgException(message);
        }
        Logger.e("HttpClient", "ErrorAction throwable.getMessage() = "
                + message + "\tErrorCode = " + exception.getErrorCode());
        doNext(exception);
    }

    public void doNext(QiufgException e) {
    }
}
