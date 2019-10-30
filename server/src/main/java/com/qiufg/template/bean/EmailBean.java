package com.qiufg.template.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Created by qiufg on 2019/10/30 13:09.
 * <p>
 * Desc：模拟测试Bean
 */
public class EmailBean implements Parcelable {

    private int id;
    private String from;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.from);
        dest.writeString(this.content);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     *
     * @param in Parcel
     */
    public void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.from = in.readString();
        this.content = in.readString();
    }

    public EmailBean() {
    }

    protected EmailBean(Parcel in) {
        this.id = in.readInt();
        this.from = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<EmailBean> CREATOR = new Parcelable.Creator<EmailBean>() {
        @Override
        public EmailBean createFromParcel(Parcel source) {
            return new EmailBean(source);
        }

        @Override
        public EmailBean[] newArray(int size) {
            return new EmailBean[size];
        }
    };
}
