package com.qiufg.template.bean;

/**
 * Created by fengguang.qiu on 2019/09/18 15:16.
 * <p>
 * Desc：分类类别
 */
public enum Category {

    All(0, "all", "全部"),
    Android(1, "Android", "Android"),
    iOS(2, "iOS", "iOS"),
    App(3, "App", "App"),
    User_Experience(4, "前端", "前端"),
    Recommend(5, "瞎推荐", "瞎推荐"),
    Expand(6, "拓展资源", "拓展资源"),
    Break_Video(7, "休息视频", "休息视频");

    private int index;
    private String type;
    private String title;

    Category(int index, String type, String title) {
        this.index = index;
        this.type = type;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public static Category getCategoryByIndex(int index) {
        for (Category modules : Category.values()) {
            if (modules.index == index) {
                return modules;
            }
        }
        return All;
    }
}
