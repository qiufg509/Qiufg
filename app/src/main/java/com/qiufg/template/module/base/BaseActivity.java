package com.qiufg.template.module.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import com.gyf.immersionbar.ImmersionBar;

/**
 * Created by fengguang.qiu on 2019/09/25 10:56.
 * <p>
 * Desc：基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
        ImmersionBar.with(this).navigationBarEnable(false).init();
        initBeforeView();
        viewCreated();
    }

    protected abstract @LayoutRes
    int createView();

    protected abstract void viewCreated();

    protected void initBeforeView() {
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }
}
