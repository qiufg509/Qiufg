package com.qiufg.template.module.note.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qiufg.template.R;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.note.presenter.NotePresenter;

import butterknife.BindView;

/**
 * Created by fengguang.qiu on 2019/10/08 10:11.
 * <p>
 * Desc：Note页面
 */
public class NoteFragment extends BaseFragment<NotePresenter> implements NoteView {

    private static final String ARG_TITLE = "title";
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private String mTitleString;

    public static NoteFragment newInstance(String title) {
        NoteFragment fragment = new NoteFragment();
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
        return R.layout.fragment_note;
    }

    @Override
    protected NotePresenter initPresenter() {
        return new NotePresenter();
    }

    @Override
    protected void viewCreated(View view) {
        mTvTitle.setText(mTitleString);
    }
}
