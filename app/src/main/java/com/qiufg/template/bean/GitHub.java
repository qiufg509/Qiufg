package com.qiufg.template.bean;

import java.util.List;

/**
 * Created by qiufg on 2019/10/11 9:35.
 * <p>
 * Desc：我的笔记数据bean
 */
public class GitHub {
    /**
     * name : .gitignore
     * path : .gitignore
     * sha : 115a49d707e26ac2d9e3cb319c401c2478138a6b
     * size : 7
     * url : https://api.github.com/repos/qiufg509/MyNote/contents/.gitignore?ref=master
     * html_url : https://github.com/qiufg509/MyNote/blob/master/.gitignore
     * git_url : https://api.github.com/repos/qiufg509/MyNote/git/blobs/115a49d707e26ac2d9e3cb319c401c2478138a6b
     * download_url : https://raw.githubusercontent.com/qiufg509/MyNote/master/.gitignore
     * type : file
     * _links : {"self":"https://api.github.com/repos/qiufg509/MyNote/contents/.gitignore?ref=master","git":"https://api.github.com/repos/qiufg509/MyNote/git/blobs/115a49d707e26ac2d9e3cb319c401c2478138a6b","html":"https://github.com/qiufg509/MyNote/blob/master/.gitignore"}
     */

    private String name;
    private String path;
    private String sha;
    private int size;
    private String url;
    private String html_url;
    private String git_url;
    private String download_url;
    private String type;
    private List<GitHub> mGitHubs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GitHub> getGitHubs() {
        return mGitHubs;
    }

    public void setGitHubs(List<GitHub> gitHubs) {
        mGitHubs = gitHubs;
    }
}
