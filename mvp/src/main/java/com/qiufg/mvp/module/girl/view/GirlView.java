package com.qiufg.mvp.module.girl.view;

import com.qiufg.mvp.module.base.IView;

/**
 * Created by fengguang.qiu on 2019/08/12 17:51.
 * <p>
 * Desc：首页（美女们）View接口
 */
public interface GirlView extends IView {

    void getDataSuccess();

    void getDataFail();
}
