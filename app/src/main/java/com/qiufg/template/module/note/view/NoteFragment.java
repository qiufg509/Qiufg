package com.qiufg.template.module.note.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.qiufg.template.R;
import com.qiufg.template.adapter.NoteAdapter;
import com.qiufg.template.bean.GitHub;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.common.WebActivity;
import com.qiufg.template.module.note.presenter.NotePresenter;
import com.qiufg.template.util.DisplayUtils;
import com.qiufg.template.wedget.decoration.NoteDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by fengguang.qiu on 2019/10/08 10:11.
 * <p>
 * Desc：Note页面
 */
public class NoteFragment extends BaseFragment<NotePresenter> implements NoteView {

    private static final String ARG_TITLE = "title";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.iv_note_bg)
    ImageView mIvNoteBg;

    private String mTitleString;
    private NoteAdapter mAdapter;
    private NoteDecoration mDecor;

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
        mToolbar.setTitle(mTitleString);
        if (getActivity() == null) return;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        mRecycler.setLayoutManager(gridLayoutManager);
        mDecor = new NoteDecoration();
        mAdapter = new NoteAdapter();
        mRecycler.addItemDecoration(mDecor);
        mAdapter.bindToRecyclerView(mRecycler);
        initListener();
        mPresenter.getArticle();
        setNoteBackground();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GitHub gitHub = (GitHub) adapter.getData().get(position);
            WebActivity.startPreview(getActivity(), gitHub.getDownload_url(), gitHub.getName());
        });
    }

    @Override
    public void getArtSuccess(List<GitHub> gitHubs) {
        mDecor.setGitHubs(gitHubs);
        mAdapter.setNewData(gitHubs);
    }

    @Override
    public void getArtFail(QiufgException e) {
        mAdapter.setNewData(null);
        mAdapter.setEmptyView(getEmptyView(e));
    }

    private void setNoteBackground() {
        Glide.with(this).asBitmap().load(R.mipmap.img_note_bg).into(new ImageViewTarget<Bitmap>(mIvNoteBg) {
            @Override
            protected void setResource(@Nullable Bitmap resource) {
                if (resource == null) return;
                int windowHeight = DisplayUtils.getWindowHeight();
                int width = resource.getWidth();
                int height = resource.getHeight();
                if (height < windowHeight) return;
                Bitmap bitmap = Bitmap.createBitmap(resource, 0, height - windowHeight, width, windowHeight);
                view.setImageBitmap(bitmap);
            }
        });
    }
}
