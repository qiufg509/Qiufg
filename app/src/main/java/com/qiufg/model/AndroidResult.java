package com.qiufg.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author qiufg
 * Date 2017/3/6
 */

public class AndroidResult {
    private boolean error;
    private @SerializedName("results")
    List<AndroidBean> mList;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<AndroidBean> getList() {
        return mList;
    }

    public void setList(List<AndroidBean> list) {
        mList = list;
    }
}
