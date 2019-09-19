package com.qiufg.mvp.module.category.view;

import com.qiufg.mvp.bean.GankBean;
import com.qiufg.mvp.exception.QiufgException;
import com.qiufg.mvp.module.base.IView;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/09/18 10:40.
 * <p>
 * Desc：分类页View接口
 */
public interface CategoryView extends IView {

    void getGankSuccess(List<GankBean> gankBeans);

    void getGankFail(QiufgException e);
}
