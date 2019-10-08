package com.qiufg.template.module.category.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.qiufg.template.R;
import com.qiufg.template.adapter.GankAdapter;
import com.qiufg.template.bean.GankBean;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.category.presenter.CategoryPresenter;
import com.qiufg.template.module.common.WebActivity;
import com.qiufg.template.util.ToastUtils;
import com.qiufg.template.wedget.GankDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by fengguang.qiu on 2019/09/18 14:46.
 * <p>
 * Desc：
 */
public class GankFragment extends BaseFragment<CategoryPresenter> implements CategoryView {

    private static final String ARG_TYPE = "type";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;

    private String mType;
    private GankAdapter mAdapter;

    public static GankFragment newInstance(String type) {
        GankFragment fragment = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    protected int createView() {
        return R.layout.fragment_gank;
    }

    @Override
    protected CategoryPresenter initPresenter() {
        return new CategoryPresenter();
    }

    @Override
    protected void viewCreated(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new GankDecoration());
        mAdapter = new GankAdapter();
        mRecycler.setAdapter(mAdapter);
        initListener();
        mPresenter.initData(mType);
    }

    private void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.refreshData(mType);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mPresenter.loadMoreData(mType);
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GankBean gankBean = (GankBean) adapter.getData().get(position);
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra(WebActivity.EXTRA_URL, gankBean.getUrl());
            intent.putExtra(WebActivity.EXTRA_TITLE, gankBean.getDesc());
            startActivity(intent);
        });
    }

    @Override
    public void getGankSuccess(List<GankBean> gankBeans) {
        mRefreshLayout.finishRefreshing();
        mRefreshLayout.finishLoadmore();
        if (mPresenter.mPage == 1) {
            mAdapter.setNewData(gankBeans);
        } else {
            mAdapter.addData(gankBeans);
        }
    }

    @Override
    public void getGankFail(QiufgException e) {
        if (mPresenter.mPage == 1) {
            mAdapter.setNewData(null);
            mAdapter.setEmptyView(getEmptyView(e));
        } else {
            ToastUtils.toast(e.getMessage());
        }
        mRefreshLayout.finishRefreshing();
        mRefreshLayout.finishLoadmore();
    }
}
