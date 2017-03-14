package com.qiufg.model;

import com.qiufg.model.base.Basebean;

import java.util.List;

/**
 * Author qiufg
 * Date 2017/3/6
 */

public class AndroidBean extends Basebean {
    /**
     * _id : 58bb93d9421aa90f0334511f
     * createdAt : 2017-03-05T12:28:09.166Z
     * desc : Android Markdown 解析库，做的很棒，很有用。
     * images : ["http://img.gank.io/2c55cb54-4321-4b62-8969-50fe15a42e55","http://img.gank.io/736520a8-ce61-4f1b-ad71-7773cc9a173b","http://img.gank.io/29c52aef-9fc2-45e5-828c-04705babb4f9"]
     * publishedAt : 2017-03-06T11:17:33.140Z
     * source : chrome
     * type : Android
     * url : https://github.com/tiagohm/MarkdownView
     * used : true
     * who : Jason
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}