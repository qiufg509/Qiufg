package com.qiufg.template.module.common;

import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.template.App;
import com.qiufg.template.R;
import com.qiufg.template.andfix.BugFixManager;
import com.qiufg.template.module.base.BaseActivity;
import com.qiufg.template.util.ToastUtils;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.content.res.SkinCompatResources;

/**
 * Created by qiufg on 2019/11/1 15:48.
 * <p>
 * Desc：andfix热修复
 */
public class AndFixActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_method)
    TextView mTvMethod;

    @Override
    protected int createView() {
        return R.layout.activity_andfix;
    }

    @Override
    protected void viewCreated() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .statusBarColorInt(SkinCompatResources.getColor(App.getInstance(), R.color.colorPrimary))
                .init();
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (title != null) {
            mTvTitle.setText(title);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_method, R.id.tv_fix})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_method:
                destination();
                break;
            case R.id.tv_fix:
                BugFixManager.getInstance().fix(getExternalFilesDir(null) + File.separator + "patch.dex");
                break;
        }
    }

    public void destination() {
        int args1 = 12;
        int args2 = 0;
        ToastUtils.toast(String.format(Locale.CHINA, "%1d / %2d = %3d", args1, args2, args1 / args2));
    }
}
