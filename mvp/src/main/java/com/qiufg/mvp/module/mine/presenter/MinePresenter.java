package com.qiufg.mvp.module.mine.presenter;

import com.qiufg.mvp.module.base.BasePresenter;
import com.qiufg.mvp.module.mine.model.MineModel;
import com.qiufg.mvp.module.mine.view.MineView;

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
