package com.qiufg.mvp.module.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.mvp.App;
import com.qiufg.mvp.R;
import com.qiufg.mvp.exception.QiufgCode;
import com.qiufg.mvp.exception.QiufgException;
import com.qiufg.mvp.util.CommonUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fengguang.qiu on 2019/08/12 15:44.
 * <p>
 * Desc：基类Fragment
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView {

    protected P mPresenter;
    private View mRootView;
    private Unbinder mBinder;
    private ViewGroup mHintLayout;
    protected Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (createView() != 0) {
            mRootView = inflater.inflate(createView(), container, false);
        }
        mPresenter = initPresenter();
        if (mPresenter == null) {
            throw new IllegalStateException("Please call mPresenter in BaseFragment(initPresenter) to onCreateView!");
        } else {
            //noinspection unchecked
            mPresenter.attach(this);
        }
        mBinder = ButterKnife.bind(this, mRootView);
        mToolbar = mRootView.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            ImmersionBar.setTitleBar(this, mToolbar);
        }
        viewCreated(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        if (mBinder != null) {
            mBinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getRefWatcher().watch(this);
    }

    protected abstract @LayoutRes
    int createView();

    protected abstract P initPresenter();

    protected abstract void viewCreated(View view);

    @Override
    public void showLoading() {
        if (getActivity() != null) {
//            mStatusManager.showLoading(getActivity());
        }
    }

    protected ViewGroup getEmptyView(QiufgException e) {
        if (e.getErrorCode() == QiufgCode.CODE_DATA_EMPTY) {
            return getHintLayout(R.mipmap.icon_hint_empty, R.string.hint_layout_no_data);
        } else if (CommonUtils.isNetworkAvailable()) {// 判断当前网络是否可用
            return getHintLayout(R.mipmap.icon_hint_request, R.string.hint_layout_error_request);
        } else {
            return getHintLayout(R.mipmap.icon_hint_nerwork, R.string.hint_layout_error_network);
        }
    }

    /**
     * 获取提示的布局
     */
    private ViewGroup getHintLayout(@DrawableRes int iconId, @StringRes int textId) {
        if (mHintLayout == null) {
            mHintLayout = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.widget_hint_layout, null);
        }
        ImageView imageView = mHintLayout.findViewById(R.id.iv_hint_icon);
        TextView textView = mHintLayout.findViewById(R.id.iv_hint_text);
        if (imageView != null) {
            imageView.setImageDrawable(getResources().getDrawable(iconId));
        }
        if (textView != null) {
            textView.setText(getString(textId));
        }
        return mHintLayout;
    }
}
