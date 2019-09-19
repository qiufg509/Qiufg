package com.qiufg.mvp.module.home.view;

import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.exception.QiufgException;
import com.qiufg.mvp.module.base.IView;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/08/12 17:51.
 * <p>
 * Desc：首页（美女们）View接口
 */
public interface HomeView extends IView {

    void setBannerImages(List<String> images);

    void getGirlsSuccess(List<GirlsBean> girlsBeans);

    void getGirlsFail(QiufgException e);
}
