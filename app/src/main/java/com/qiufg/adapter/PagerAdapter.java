package com.qiufg.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qiufg.fragment.base.BasePageFragment;

import java.util.List;


/**
 * Author qiufg
 * Date 2017/2/20
 */

public class PagerAdapter<T extends BasePageFragment> extends FragmentPagerAdapter {
    private List<T> mList;

    public PagerAdapter(FragmentManager fm, List<T> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
}
