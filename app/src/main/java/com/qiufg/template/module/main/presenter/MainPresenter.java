package com.qiufg.template.module.main.presenter;

import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.main.model.MainModel;
import com.qiufg.template.module.main.view.MainView;

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
