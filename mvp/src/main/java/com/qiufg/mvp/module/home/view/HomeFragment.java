package com.qiufg.mvp.module.home.view;

import android.content.Context;
import android.content.Intent;
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
import com.qiufg.mvp.exception.QiufgException;
import com.qiufg.mvp.listener.OnFragmentInteractionListener;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.preview.view.PhotoPreviewActivity;
import com.qiufg.mvp.module.home.presenter.HomePresenter;
import com.qiufg.mvp.util.GlideImageLoader;
import com.qiufg.mvp.util.ToastUtils;
import com.qiufg.mvp.wedget.HomeDecoration;
import com.youth.banner.Banner;

import java.util.ArrayList;
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
    private List<String> mBannerData;

    private OnFragmentInteractionListener mListener;
    private String mTitleString;
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
        mAdapter.setHeaderAndEmpty(true);
        mRecycler.setAdapter(mAdapter);
        addHeaderView();
        mAdapter.setPreLoadNumber(1);
        initListener();
        mPresenter.initData();
    }

    private void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.refreshData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mPresenter.loadMoreData();
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
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GirlsBean girlsBean = (GirlsBean) adapter.getData().get(position);
            Intent intent = new Intent(getContext(), PhotoPreviewActivity.class);
            ArrayList<String> urls = new ArrayList<>();
            urls.add(girlsBean.getUrl());
            intent.putExtra(PhotoPreviewActivity.EXTRA_URL_LIST, urls);
            startActivity(intent);
        });
        mBanner.setOnBannerListener(position -> {
            Intent intent = new Intent(getContext(), PhotoPreviewActivity.class);
            ArrayList<String> urls = new ArrayList<>(mBannerData);
            intent.putExtra(PhotoPreviewActivity.EXTRA_URL_LIST, urls);
            intent.putExtra(PhotoPreviewActivity.EXTRA_PAGE_INDEX, position);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    @Override
    public void setBannerImages(List<String> images) {
        mBannerData = images;
        mBanner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                .start();
    }

    @Override
    public void getGirlsSuccess(List<GirlsBean> girlsBeans) {
        mRefreshLayout.finishRefreshing();
        mRefreshLayout.finishLoadmore();
        if (mPresenter.mPage == 1) {
            mAdapter.setNewData(girlsBeans);
        } else {
            mAdapter.addData(girlsBeans);
        }
    }

    @Override
    public void getGirlsFail(QiufgException e) {
        if (mPresenter.mPage == 1) {
            mAdapter.setNewData(null);
            mAdapter.setEmptyView(getEmptyView(e));
        } else {
            ToastUtils.toast(e.getMessage());
        }
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
