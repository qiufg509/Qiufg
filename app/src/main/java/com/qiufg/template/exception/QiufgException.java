package com.qiufg.template.exception;

import com.qiufg.template.net.respond.ResultArray;
import com.qiufg.template.net.respond.ResultObject;

/**
 * Created by fengguang.qiu on 2019/3/19 19:40.
 * <p>
 * Desc：自定义异常
 */
public class QiufgException extends Exception {

    private int errorCode;

    public QiufgException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
        setErrorCode(resultCode);
    }

    public QiufgException(String detailMessage) {
        super(detailMessage);
        setErrorCode(QiufgCode.CODE_OTHER);
    }

    public QiufgException(int resultCode, String detailMessage) {
        super(detailMessage);
        setErrorCode(resultCode);
    }

    public QiufgException(ResultObject resultObject) {
//        this((TextUtils.isEmpty(resultObject.getMsg()) || resultObject.getRet() == 1)
//                ? getApiExceptionMessage(resultObject.getRet())
//                : resultObject.getMsg());
//        setErrorCode(resultObject.getRet());
        this(getApiExceptionMessage(resultObject.isError() ? QiufgCode.CODE_OTHER : QiufgCode.CODE_OTHER));
        setErrorCode(QiufgCode.CODE_HTTP_ERROR);
    }

    public QiufgException(ResultArray resultArray) {
//        this((TextUtils.isEmpty(resultArray.getMsg()) || resultArray.getRet() == 1)
//                ? getApiExceptionMessage(resultArray.getRet())
//                : resultArray.getMsg());
//        setErrorCode(resultArray.getRet());
        this(getApiExceptionMessage(resultArray.isError() ? QiufgCode.CODE_OTHER : QiufgCode.CODE_OTHER));
        setErrorCode(QiufgCode.CODE_HTTP_ERROR);
    }

    public int getErrorCode() {
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
            case QiufgCode.CODE_LOGIN_INVALID:
                message = "登录信息失效";
                break;
            case QiufgCode.CODE_TIME_OUT:
                message = "连接超时";
                break;
            case QiufgCode.CODE_HTTP_ERROR:
                message = "网络错误";
                break;
            case QiufgCode.CODE_CONNECT_FAIL:
                message = "无法连接到服务器";
                break;
            case QiufgCode.CODE_SERVER_ERROR:
                message = "服务器异常";
                break;
            case QiufgCode.CODE_PARSE_ERROR:
                message = "解析错误";
                break;
            case QiufgCode.CODE_SECURITY_ERROR:
                message = "非安全请求";
                break;
            case QiufgCode.CODE_DATA_EMPTY:
                message = "没有数据";
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
