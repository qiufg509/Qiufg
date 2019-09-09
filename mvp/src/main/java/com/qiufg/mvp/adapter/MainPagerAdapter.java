package com.qiufg.mvp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qiufg.mvp.module.base.BaseFragment;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/09/03 14:09.
 * <p>
 * Desc：主页界面切换适配器
 */
public class MainPagerAdapter<T extends BaseFragment> extends FragmentPagerAdapter {

    private List<T> mFragments;

    public MainPagerAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
