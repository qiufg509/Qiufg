package com.qiufg.mvp.net;

/**
 * Created by fengguang.qiu on 2019/08/08 11:43.
 * <p>
 * Desc：HttpClient实现
 */
public class HttpClientImp extends HttpClient {

    private static HttpClientImp sHttpClient;

    private HttpClientImp() {
    }

    public static HttpClientImp getInstance() {
        if (sHttpClient == null) {
            synchronized (HttpClientImp.class) {
                if (sHttpClient == null) {
                    sHttpClient = new HttpClientImp();
                }
            }
        }
        return sHttpClient;
    }
}
