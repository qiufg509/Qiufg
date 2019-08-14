package com.qiufg.mvp.net.respond;

import androidx.annotation.NonNull;

/**
 * Created by fengguang.qiu on 2019/3/19 19:40.
 * <p>
 * Desc：接口统一解析bean（data为Object）
 */
public class ResultObject<T> {
    /**
     * error : false
     * results : {}
     */

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResultObject{" +
                "error=" + error +
                ", results=" + (results == null ? "" : results.toString()) +
                '}';
    }
}
