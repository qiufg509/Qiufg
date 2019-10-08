package com.qiufg.template.module.category.view;

import com.qiufg.template.bean.GankBean;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.IView;

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
