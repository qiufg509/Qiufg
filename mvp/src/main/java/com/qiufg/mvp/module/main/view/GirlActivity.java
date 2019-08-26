package com.qiufg.mvp.module.main.view;

import com.qiufg.mvp.R;
import com.qiufg.mvp.module.base.BaseActivity;
import com.qiufg.mvp.module.main.presenter.GirlPresenter;
import com.qiufg.mvp.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fengguang.qiu on 2019/08/07 11:43.
 * <p>
 * Desc：主页面
 */
public class GirlActivity extends BaseActivity<GirlPresenter> implements GirlView {

    @Override
    protected int createView() {
        return R.layout.activity_main;
    }

    @Override
    protected GirlPresenter createPresenter() {
        return new GirlPresenter();
    }

    @Override
    protected void viewCreated() {
        ButterKnife.bind(this);
    }

    @Override
    public void getDataSuccess() {
        ToastUtils.toast("getDataSuccess");
    }

    @Override
    public void getDataFail() {
        ToastUtils.toast("getDataFail");
    }

    @OnClick(R.id.click)
    public void onViewClicked() {
        mPresenter.getData(10, 1);
    }
}
