package com.qiufg.mvp.module.main.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.qiufg.mvp.R;
import com.qiufg.mvp.module.main.presenter.GirlPresenter;
import com.qiufg.mvp.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fengguang.qiu on 2019/08/07 11:43.
 * <p>
 * Desc：主页面
 */
public class GirlActivity extends AppCompatActivity implements GirlView {

    private GirlPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new GirlPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
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
