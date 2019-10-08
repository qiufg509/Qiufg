package com.qiufg.template.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qiufg.template.bean.Category;
import com.qiufg.template.module.base.BaseFragment;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/09/18 11:51.
 * <p>
 * Desc：分类页面切换适配器
 */
public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;

    public CategoryPagerAdapter(@NonNull FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Category.getCategoryByIndex(position).getTitle();
    }
}
