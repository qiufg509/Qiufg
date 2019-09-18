package com.qiufg.mvp.module.home.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.qiufg.mvp.App;
import com.qiufg.mvp.R;
import com.qiufg.mvp.adapter.HomeAdapter;
import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.listener.OnFragmentInteractionListener;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.home.presenter.HomePresenter;
import com.qiufg.mvp.util.GlideImageLoader;
import com.qiufg.mvp.wedget.HomeDecoration;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

/**
 * Created by fengguang.qiu on 2019/08/12 17:51.
 * <p>
 * Desc：首页（美女们）页面
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {

    private static final String ARG_TITLE = "title";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    Banner mBanner;

    private String mTitleString;
    private int mPage = 1;

    private OnFragmentInteractionListener mListener;
    private HomeAdapter mAdapter;
    private int mBannerHeight;

    public static HomeFragment newInstance(String title) {
        HomeFragment fragment = new HomeFragment();
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
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onAttach(@NonNull Context context) {
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
        mTvTitle.setText(mTitleString);
        if (getActivity() == null) return;
        mAdapter = new HomeAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), HomeDecoration.SPAN_COUNT);
        mRecycler.setLayoutManager(gridLayoutManager);
        mRecycler.addItemDecoration(new HomeDecoration());
        mRecycler.setAdapter(mAdapter);
        addHeaderView();
        mAdapter.setPreLoadNumber(1);
        initListener();
        mPresenter.getData(mPage);
    }

    private void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.refreshData(mPage = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mPresenter.getGirlData(++mPage);
            }
        });
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mTotalDy = 0;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalDy += dy;
                if (mTotalDy <= mBannerHeight) {
                    float alpha = (float) mTotalDy / mBannerHeight;
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(App.getInstance(), R.color.colorPrimary), alpha));
                    mTvTitle.setTextColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , Color.WHITE, alpha));
                } else {
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(App.getInstance(), R.color.colorPrimary), 1));
                    mTvTitle.setTextColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , Color.WHITE, 1));
                }
            }
        });
    }

    @Override
    public void setBannerImages(List<String> images) {
        mBanner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                .start();
    }

    @Override
    public void getGirlsSuccess(List<GirlsBean> girlsBeans) {
        mRefreshLayout.finishRefreshing();
        mRefreshLayout.finishLoadmore();
        if (girlsBeans == null || girlsBeans.isEmpty()) {
            mAdapter.setEmptyView(getEmptyView());
        } else {
            if (mPage == 1) {
                mAdapter.setNewData(girlsBeans);
            } else {
                mAdapter.addData(girlsBeans);
            }
        }
    }

    @Override
    public void getGirlsFail() {
        mAdapter.setEmptyView(getErrorView());
        mRefreshLayout.finishRefreshing();
        mRefreshLayout.finishLoadmore();
    }

    private void addHeaderView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_banner, (ViewGroup) mRecycler.getParent(), false);
        mBanner = headView.findViewById(R.id.banner);
        mAdapter.addHeaderView(headView);
        ViewGroup.LayoutParams bannerParams = mBanner.getLayoutParams();
        ViewGroup.LayoutParams titleBarParams = mToolbar.getLayoutParams();
        mBannerHeight = bannerParams.height - titleBarParams.height - ImmersionBar.getStatusBarHeight(this);
    }
}
