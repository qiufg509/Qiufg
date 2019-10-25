package com.qiufg.template.module.main.view;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.template.App;
import com.qiufg.template.R;
import com.qiufg.template.adapter.MainPagerAdapter;
import com.qiufg.template.listener.OnFragmentInteractionListener;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.base.MVPActivity;
import com.qiufg.template.module.main.presenter.MainPresenter;
import com.qiufg.template.util.ToastUtils;
import com.qiufg.template.wedget.CustomViewPager;

import java.util.List;

import butterknife.BindView;
import skin.support.design.widget.SkinMaterialBottomNavigationView;

/**
 * Created by fengguang.qiu on 2019/08/8 18:09.
 * <p>
 * Desc：主界面Activity
 */
public class MainActivity extends MVPActivity<MainPresenter> implements MainView,
        ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener,
        OnFragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int NAVI_INDEX_HOME = 0;
    private static final int NAVI_INDEX_CATEGORY = 1;
    private static final int NAVI_INDEX_NOTE = 2;
    private static final int NAVI_INDEX_MINE = 3;
    @BindView(R.id.viewPager)
    CustomViewPager mViewPager;
    @BindView(R.id.nav_view)
    SkinMaterialBottomNavigationView mNaviView;

    private long[] mHits = new long[2];

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
        mViewPager.addOnPageChangeListener(this);

        List<BaseFragment> fragments = mPresenter.getFragments();
        MainPagerAdapter pagerAdapter = new MainPagerAdapter<>(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(fragments.size() - 1);//用来控制Fragment不重走生命周期的方法
        mViewPager.setAdapter(pagerAdapter);
        mNaviView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case NAVI_INDEX_HOME:
                mNaviView.setSelectedItemId(R.id.navigation_home);
                break;
            case NAVI_INDEX_CATEGORY:
                mNaviView.setSelectedItemId(R.id.navigation_category);
                break;
            case NAVI_INDEX_NOTE:
                mNaviView.setSelectedItemId(R.id.navigation_note);
                break;
            case NAVI_INDEX_MINE:
                mNaviView.setSelectedItemId(R.id.navigation_mine);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                ImmersionBar.with(this).keyboardEnable(false).transparentBar().statusBarDarkFont(false).init();
                mViewPager.setCurrentItem(NAVI_INDEX_HOME,
                        mViewPager.getCurrentItem() == NAVI_INDEX_CATEGORY);
                return true;
            case R.id.navigation_category:
                ImmersionBar.with(this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).init();
                mViewPager.setCurrentItem(NAVI_INDEX_CATEGORY,
                        mViewPager.getCurrentItem() == NAVI_INDEX_HOME
                                || mViewPager.getCurrentItem() == NAVI_INDEX_NOTE);
                return true;
            case R.id.navigation_note:
                ImmersionBar.with(this).keyboardEnable(false).transparentBar().statusBarDarkFont(false).init();
                mViewPager.setCurrentItem(NAVI_INDEX_NOTE,
                        mViewPager.getCurrentItem() == NAVI_INDEX_CATEGORY
                                || mViewPager.getCurrentItem() == NAVI_INDEX_MINE);
                return true;
            case R.id.navigation_mine:
                ImmersionBar.with(this).keyboardEnable(false).transparentBar().statusBarDarkFont(false).init();
                mViewPager.setCurrentItem(NAVI_INDEX_MINE,
                        mViewPager.getCurrentItem() == NAVI_INDEX_NOTE);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 3500)) {
            super.onBackPressed();
            App.getInstance().clearTask();
        } else {
            ToastUtils.toast("再按一次退出应用");
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mNaviView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }
}
