package com.qiufg.model;

import com.qiufg.model.base.Basebean;

/**
 * Author qiufg
 * Date 2017/2/20
 */

public class GirlBean extends Basebean {

    /**
     * _id : 58a641a4421aa9662f429734
     * createdAt : 2017-02-17T08:19:48.413Z
     * desc : 2-17
     * publishedAt : 2017-02-17T11:31:19.996Z
     * source : chrome
     * type : 福利
     * url : http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-17-16464474_721724277990542_654863958657728512_n.jpg
     * used : true
     * who : daimajia
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
}
