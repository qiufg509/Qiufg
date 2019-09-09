package com.qiufg.mvp.module.main.model;

import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.girl.view.GirlFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengguang.qiu on 2019/08/29 11:45.
 * <p>
 * Desc：主界面Model
 */
public class MainModel {

    public List<BaseFragment> getData() {
        List<BaseFragment> fragments = new ArrayList<>();
        GirlFragment girlFragment1 = GirlFragment.newInstance("美女们1");
        GirlFragment girlFragment2 = GirlFragment.newInstance("美女们2");
        GirlFragment girlFragment3 = GirlFragment.newInstance("美女们3");
        GirlFragment girlFragment4 = GirlFragment.newInstance("美女们4");
        fragments.add(girlFragment1);
        fragments.add(girlFragment2);
        fragments.add(girlFragment3);
        fragments.add(girlFragment4);
        return fragments;
    }
}
