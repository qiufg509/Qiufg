package com.qiufg.mvp.module.category.view;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qiufg.mvp.R;
import com.qiufg.mvp.adapter.CategoryPagerAdapter;
import com.qiufg.mvp.bean.Category;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.category.presenter.CategoryPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by fengguang.qiu on 2019/09/18 10:42.
 * <p>
 * Desc：分类页面
 */
public class CategoryFragment extends BaseFragment<CategoryPresenter> {

    private static final String ARG_TITLE = "title";
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private String mTitleString;

    public static CategoryFragment newInstance(String title) {
        CategoryFragment fragment = new CategoryFragment();
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
        return R.layout.fragment_category;
    }

    @Override
    protected CategoryPresenter initPresenter() {
        return new CategoryPresenter();
    }

    @Override
    protected void viewCreated(View view) {
        if (mToolbar != null) {
            mToolbar.setTitle(mTitleString);
        }
        if (getActivity() == null) return;
        for (Category value : Category.values()) {
            mTabLayout.addTab(mTabLayout.newTab().setText(value.getTitle()));
        }
        List<BaseFragment> fragments = mPresenter.getFragments();
        mViewPager.setAdapter(new CategoryPagerAdapter(getChildFragmentManager(), fragments));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
