package com.qiufg.mvp.module.girl.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.qiufg.mvp.R;
import com.qiufg.mvp.listener.OnFragmentInteractionListener;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.girl.presenter.GirlPresenter;
import com.youth.banner.Banner;

import butterknife.BindView;

/**
 * Created by fengguang.qiu on 2019/08/12 17:51.
 * <p>
 * Desc：首页（美女们）页面
 */
public class GirlFragment extends BaseFragment<GirlPresenter> implements GirlView {

    private static final String ARG_TITLE = "title";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;

    private String mTitleString;

    private OnFragmentInteractionListener mListener;

    public static GirlFragment newInstance(String title) {
        GirlFragment fragment = new GirlFragment();
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
        return R.layout.fragment_girl;
    }

    @Override
    protected GirlPresenter initPresenter() {
        return new GirlPresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void viewCreated(View view) {
        mToolbar.setTitle(mTitleString);
        mPresenter.getHeadImage(5, 1);
//        mBanner.setImages(mImages)
//                .setImageLoader(new GlideImageLoader())
//                .setDelayTime(5000)
//                .start();
    }

    @Override
    public void getDataSuccess() {
        mStatusManager.showComplete();
    }

    @Override
    public void getDataFail() {
        mStatusManager.showError(mRefreshLayout);
        mRecycler.setVisibility(View.GONE);
    }
}
