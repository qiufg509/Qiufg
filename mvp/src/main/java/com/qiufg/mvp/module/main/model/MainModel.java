package com.qiufg.mvp.module.main.model;

import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.category.view.CategoryFragment;
import com.qiufg.mvp.module.home.view.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengguang.qiu on 2019/08/29 11:45.
 * <p>
 * Desc：主界面Model
 */
public class MainModel {

    public List<BaseFragment> getFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        HomeFragment girlFragment1 = HomeFragment.newInstance("美女们");
        CategoryFragment categoryFragment = CategoryFragment.newInstance("gank.io");
        HomeFragment girlFragment3 = HomeFragment.newInstance("美女们3");
        HomeFragment girlFragment4 = HomeFragment.newInstance("美女们4");
        fragments.add(girlFragment1);
        fragments.add(categoryFragment);
        fragments.add(girlFragment3);
        fragments.add(girlFragment4);
        return fragments;
    }
}
