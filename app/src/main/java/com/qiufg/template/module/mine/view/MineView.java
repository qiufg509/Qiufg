package com.qiufg.template.module.mine.view;

import com.qiufg.template.bean.User;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.IView;

/**
 * Created by fengguang.qiu on 2019/10/08 10:17.
 * <p>
 * Desc：我的页View接口
 */
public interface MineView extends IView {

    void getUserSuccess(User user);

    void getUserFail(QiufgException e);
}
