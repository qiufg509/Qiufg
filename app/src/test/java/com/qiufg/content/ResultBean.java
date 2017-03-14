package com.qiufg.content;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author qiufg
 * Date 2017/3/6
 */

public class ResultBean {
    private boolean error;
    private
    @SerializedName("results")
    List<ContentBean> mList;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ContentBean> getList() {
        return mList;
    }

    public void setList(List<ContentBean> list) {
        mList = list;
    }
}
