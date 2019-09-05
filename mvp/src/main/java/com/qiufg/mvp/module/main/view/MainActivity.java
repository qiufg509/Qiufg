package com.qiufg.mvp.module.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.qiufg.mvp.R;
import com.qiufg.mvp.adapter.MainPagerAdapter;
import com.qiufg.mvp.listener.OnFragmentInteractionListener;
import com.qiufg.mvp.module.base.BaseActivity;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.main.presenter.MainPresenter;
import com.qiufg.mvp.wedget.CustomViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView, ViewPager.OnPageChangeListener, OnFragmentInteractionListener {

    private static final int NAVI_INDEX_HOME = 0;
    private static final int NAVI_INDEX_CATEGORY = 1;
    private static final int NAVI_INDEX_SERVICE = 2;
    private static final int NAVI_INDEX_MINE = 3;
    @BindView(R.id.viewPager)
    CustomViewPager mViewPager;
    @BindView(R.id.ll_home)
    LinearLayout mLayoutHome;
    @BindView(R.id.ll_category)
    LinearLayout mLayoutCategory;
    @BindView(R.id.ll_service)
    LinearLayout mLayoutService;
    @BindView(R.id.ll_mine)
    LinearLayout mLayoutMine;

    @Override
    protected int createView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void viewCreated() {
        ButterKnife.bind(this);
        mViewPager.addOnPageChangeListener(this);

        List<BaseFragment> fragments = mPresenter.getFragments();
        MainPagerAdapter pagerAdapter = new MainPagerAdapter<>(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(3);//用来控制Fragment不重走生命周期的方法
        mViewPager.setAdapter(pagerAdapter);
        mLayoutHome.setSelected(true);
    }

    @OnClick({R.id.ll_home, R.id.ll_category, R.id.ll_service, R.id.ll_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                mViewPager.setCurrentItem(NAVI_INDEX_HOME);
                tabSelected(mLayoutHome);
                break;
            case R.id.ll_category:
                mViewPager.setCurrentItem(NAVI_INDEX_CATEGORY);
                tabSelected(mLayoutCategory);
                break;
            case R.id.ll_service:
                mViewPager.setCurrentItem(NAVI_INDEX_SERVICE);
                tabSelected(mLayoutService);
                break;
            case R.id.ll_mine:
                mViewPager.setCurrentItem(NAVI_INDEX_MINE);
                tabSelected(mLayoutMine);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case NAVI_INDEX_HOME:
                tabSelected(mLayoutHome);
                break;
            case NAVI_INDEX_CATEGORY:
                tabSelected(mLayoutCategory);
                break;
            case NAVI_INDEX_SERVICE:
                tabSelected(mLayoutService);
                break;
            case NAVI_INDEX_MINE:
                tabSelected(mLayoutMine);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void tabSelected(LinearLayout layout) {
        mLayoutHome.setSelected(false);
        mLayoutCategory.setSelected(false);
        mLayoutService.setSelected(false);
        mLayoutMine.setSelected(false);
        layout.setSelected(true);
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }
}
