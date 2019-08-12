package com.qiufg.mvp.exception;

import android.text.TextUtils;

import com.qiufg.mvp.net.respond.ResultArray;
import com.qiufg.mvp.net.respond.ResultObject;

/**
 * Created by fengguang.qiu on 2019/3/19 19:40.
 * <p>
 * Desc：自定义异常
 */
public class ForestException extends Exception {

    private int errorCode;

    public ForestException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
        setErrorCode(resultCode);
    }

    public ForestException(String detailMessage) {
        super(detailMessage);
        setErrorCode(ForestCode.CODE_OTHER);
    }

    public ForestException(int resultCode, String detailMessage) {
        super(detailMessage);
        setErrorCode(resultCode);
    }

    public ForestException(ResultObject resultObject) {
        this((TextUtils.isEmpty(resultObject.getMsg()) || resultObject.getRet() == 1)
                ? getApiExceptionMessage(resultObject.getRet())
                : resultObject.getMsg());
        setErrorCode(resultObject.getRet());
    }

    public ForestException(ResultArray resultArray) {
        this((TextUtils.isEmpty(resultArray.getMsg()) || resultArray.getRet() == 1)
                ? getApiExceptionMessage(resultArray.getRet())
                : resultArray.getMsg());
        setErrorCode(resultArray.getRet());
    }

    int getErrorCode() {
        return errorCode;
    }

    private void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code 错误码
     * @return 自定义错误信息
     */
    private static String getApiExceptionMessage(int code) {
        String message;
        switch (code) {
            case ForestCode.CODE_LOGIN_INVALID:
                message = "登录信息失效";
                break;
            case ForestCode.CODE_TIME_OUT:
                message = "连接超时";
                break;
            case ForestCode.CODE_HTTP_ERROR:
                message = "网络错误";
                break;
            case ForestCode.CODE_CONNECT_FAIL:
                message = "无法连接到服务器";
                break;
            case ForestCode.CODE_SERVER_ERROR:
                message = "服务器异常";
                break;
            case ForestCode.CODE_PARSE_ERROR:
                message = "解析错误";
                break;
            case ForestCode.CODE_INTERFACE_ERROR:
                message = "服务器接口错误";
                break;
            case ForestCode.CODE_SECURITY_ERROR:
                message = "非安全请求";
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
