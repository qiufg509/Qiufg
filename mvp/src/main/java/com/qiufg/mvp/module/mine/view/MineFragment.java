package com.qiufg.mvp.module.mine.view;

import android.os.Bundle;
import android.view.View;

import com.qiufg.mvp.R;
import com.qiufg.mvp.listener.OnFragmentInteractionListener;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.mine.presenter.MinePresenter;

/**
 * Created by fengguang.qiu on 2019/10/08 10:20.
 * <p>
 * Desc：我的页
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineView {

    private static final String ARG_TITLE = "title";

    private OnFragmentInteractionListener mListener;
    private String mTitleString;

    public static MineFragment newInstance(String title) {
        MineFragment fragment = new MineFragment();
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
        return R.layout.fragment_mine;
    }

    @Override
    protected MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void viewCreated(View view) {

    }
}
