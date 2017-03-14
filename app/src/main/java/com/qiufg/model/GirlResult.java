package com.qiufg.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author qiufg
 * Date 2017/2/20
 */

public class GirlResult {

    private boolean error;
    private  @SerializedName("results") List<GirlBean> mList;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GirlBean> getList() {
        return mList;
    }

    public void setList(List<GirlBean> list) {
        mList = list;
    }
}
