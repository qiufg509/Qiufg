package com.qiufg.mvp.bean;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/09/18 14:38.
 * <p>
 * Desc：http://gank.io/api/data/{type}/10/1 接口返回数据bean
 */
public class GankBean {
    /**
     * _id : 5cd527ed9d212239df927aa2
     * createdAt : 2019-05-10T07:27:41.223Z
     * desc : GoPlay 是一款基于FFmpeg/OpenGL ES 2.0 的iOS播放器。支持FFmpeg内嵌的所有格式。而且可以自定义各种滤镜, 包括VR、水印等。
     * images : ["https://ww1.sinaimg.cn/large/0073sXn7gy1g37vymj8etj30ij0agn42","https://ww1.sinaimg.cn/large/0073sXn7gy1g37vynuh2uj30ik0agdrz","https://ww1.sinaimg.cn/large/0073sXn7gy1g37vyqf0gvj30il0aedof"]
     * publishedAt : 2019-05-20T08:49:42.980Z
     * source : web
     * type : iOS
     * url : https://github.com/dKingbin/GoPlay
     * used : true
     * who : lijinshanmx
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
