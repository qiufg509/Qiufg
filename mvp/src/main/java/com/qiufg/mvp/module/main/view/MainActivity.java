package com.qiufg.mvp.module.main.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qiufg.mvp.R;
import com.qiufg.mvp.module.base.BaseActivity;
import com.qiufg.mvp.module.main.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @Override
    protected int createView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void viewCreated() {

    }
}
