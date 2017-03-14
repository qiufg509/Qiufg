package com.qiufg.content;

/**
 * Author qiufg
 * Date 2017/3/6
 */

public class ContentBean {

    /**
     * _id : 58b7f064421aa90efc4fb644
     * createdAt : 2017-03-02T18:13:56.571Z
     * desc : 浅谈RxJava与多线程并发
     * publishedAt : 2017-03-03T12:13:36.333Z
     * source : web
     * type : Android
     * url : http://www.zjutkz.net/2017/02/09/%E6%B5%85%E8%B0%88RxJava%E4%B8%8E%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%B9%B6%E5%8F%91/
     * used : true
     * who : null
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private Object who;

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

    public Object getWho() {
        return who;
    }

    public void setWho(Object who) {
        this.who = who;
    }
}
