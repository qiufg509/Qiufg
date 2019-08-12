package com.qiufg.mvp.exception;

/**
 * Created by fengguang.qiu on 2019/3/19 19:40.
 * <p>
 * Desc：请求响应码
 */
public interface ForestCode {

    /**
     * 网络请求成功
     */
    int CODE_RESPONSE_SUCCESS = 0;
    int CODE_INTERFACE_ERROR = 1;
    int CODE_VIP_MEMBER_EXPIRED = 14003;

    int CODE_TIME_OUT = 45401;
    int CODE_CONNECT_FAIL = 45402;
    int CODE_SERVER_ERROR = 45403;
    int CODE_HTTP_ERROR = 45404;
    int CODE_PARSE_ERROR = 45405;
    int CODE_SECURITY_ERROR = 45406;

    int CODE_LOGIN_INVALID = 10000;

    int CODE_OTHER = 45499;
    //read me：因为后台接口错误码定义多有重复重叠，不方便全局定义其余错误码
}
