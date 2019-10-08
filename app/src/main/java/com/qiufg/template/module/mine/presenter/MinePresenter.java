package com.qiufg.template.module.mine.presenter;

import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.mine.model.MineModel;
import com.qiufg.template.module.mine.view.MineView;

/**
 * Created by fengguang.qiu on 2019/10/08 10:18.
 * <p>
 * Desc：我的页Presenter
 */
public class MinePresenter extends BasePresenter<MineView> {

    private MineModel mModel;

    public MinePresenter() {
        mModel = new MineModel();
    }
}
