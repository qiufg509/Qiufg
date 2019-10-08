package com.qiufg.template.net.respond;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/3/20 15:30.
 * <p>
 * Desc：接口统一解析bean（data为Array）
 */
public class ResultArray<T> {

    /**
     * error : false
     * results : []
     */

    private boolean error;
    private List<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResultArray{" +
                "error=" + error +
                ", results=" + (results == null ? "" : results.toString()) +
                '}';
    }
}
