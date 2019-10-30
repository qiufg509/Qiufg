package com.qiufg.template.module.mine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qiufg.template.R;
import com.qiufg.template.bean.User;
import com.qiufg.template.bus.CustomSkinLoader;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.common.AIDLActivity;
import com.qiufg.template.module.mine.presenter.MinePresenter;
import com.qiufg.template.util.Logger;

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
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_account)
    TextView mTvAccount;

    private int mIndex = 0;

    public static MineFragment newInstance(String title) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
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
        if (getActivity() == null) return;
        mPresenter.getUserInfo();
    }

    @OnClick({R.id.iv_photo_camera, R.id.iv_avatar, R.id.tv_name, R.id.tv_account,
            R.id.select_theme_style, R.id.aidl, R.id.unknown1,
            R.id.unknown2, R.id.unknown3, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo_camera:
                break;
            case R.id.iv_avatar:
                break;
            case R.id.tv_name:
            case R.id.tv_account:
                break;
            case R.id.select_theme_style:
                selectThemeStyle();
                break;
            case R.id.aidl:
                AIDLActivity.gotoAIDLActivity(getActivity(), "IPC");
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

    @Override
    public void getUserSuccess(User user) {
        mTvName.setText(user.getName());
        mTvAccount.setText(user.getLogin());
        Glide.with(this).load(user.getAvatar_url()).apply(RequestOptions.circleCropTransform()).into(mIvAvatar);
    }

    @Override
    public void getUserFail(QiufgException e) {
        Logger.e("获取用户信息失败\te = " + e.getMessage());
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
