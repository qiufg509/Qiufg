package com.qiufg.mvp.module.main.presenter;

import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.base.BasePresenter;
import com.qiufg.mvp.module.main.model.MainModel;
import com.qiufg.mvp.module.main.view.MainView;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/08/29 11:56.
 * <p>
 * Desc：主界面Presenter
 */
public class MainPresenter extends BasePresenter<MainView> {

    private MainModel mModel;

    public MainPresenter() {
        mModel = new MainModel();
    }

    public List<BaseFragment> getFragments() {
        return mModel.getFragments();
    }

}
