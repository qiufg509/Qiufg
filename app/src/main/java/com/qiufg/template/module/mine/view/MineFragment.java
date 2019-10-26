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

    private String mTitleString;
    private int mIndex = 0;

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

    @OnClick({R.id.select_theme_style, R.id.aidl, R.id.unknown1, R.id.unknown2, R.id.unknown3, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_theme_style:
                selectThemeStyle();
                break;
            case R.id.aidl:
                break;
            case R.id.unknown1:
                break;
            case R.id.unknown2:
                break;
            case R.id.unknown3:
                break;
            case R.id.setting:
                break;
        }
    }

    private void selectThemeStyle() {
        if (mIndex++ % 2 == 0) {
            SkinCompatManager.getInstance().loadSkin("skin.skin", null,
                    CustomSkinLoader.SKIN_LOADER_STRATEGY_SDCARD);
        } else {
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
    }
}
