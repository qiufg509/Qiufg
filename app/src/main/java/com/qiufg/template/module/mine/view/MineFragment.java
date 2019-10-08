package com.qiufg.template.module.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qiufg.template.R;
import com.qiufg.template.bus.CustomSkinLoader;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.mine.presenter.MinePresenter;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.SkinCompatManager;

/**
 * Created by fengguang.qiu on 2019/10/08 10:20.
 * <p>
 * Desc：我的页
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineView {

    private static final String ARG_TITLE = "title";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_select_theme_style)
    TextView mTvSelectThemeStyle;

    private String mTitleString;

    public static MineFragment newInstance(String title) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitleString = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    protected int createView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void viewCreated(View view) {
        mTvTitle.setText(mTitleString);
        if (getActivity() == null) return;

    }

    @OnClick(R.id.tv_select_theme_style)
    public void onViewClicked() {
        // 指定皮肤插件
        SkinCompatManager.getInstance().loadSkin("new.skin", CustomSkinLoader.SKIN_LOADER_STRATEGY_SDCARD);

// 恢复应用默认皮肤
//        SkinCompatManager.getInstance().restoreDefaultTheme();
    }
}
