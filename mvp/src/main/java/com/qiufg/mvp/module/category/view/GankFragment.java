package com.qiufg.mvp.module.category.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.qiufg.mvp.R;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.category.presenter.CategoryPresenter;

import butterknife.BindView;

/**
 * Created by fengguang.qiu on 2019/09/18 14:46.
 * <p>
 * Desc：
 */
public class GankFragment extends BaseFragment<CategoryPresenter> {

    private static final String ARG_TYPE = "type";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;

    private String mType;

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

    }
}
