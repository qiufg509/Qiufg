package com.qiufg.mvp.net.respond;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/3/20 15:30.
 * <p>
 * Desc：接口统一解析bean（data为Array）
 */
public class ResultArray<T> {
    /**
     * ret : 0
     * msg :
     * data : []
     */

    private int ret;
    private String msg;
    @SerializedName("data")
    private List<T> data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResultObject{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + (data == null ? "" : data.toString()) +
                '}';
    }
}
