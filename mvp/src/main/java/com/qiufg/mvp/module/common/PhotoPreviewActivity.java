package com.qiufg.mvp.module.common;

import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.mvp.R;
import com.qiufg.mvp.adapter.PhotoPreviewAdapter;
import com.qiufg.mvp.module.base.BaseActivity;
import com.qiufg.mvp.util.ToastUtils;
import com.qiufg.mvp.wedget.CustomViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fengguang.qiu on 2019/09/26 17:49.
 * <p>
 * Desc：照片预览页
 */
public class PhotoPreviewActivity extends BaseActivity {

    public static final String EXTRA_URL_LIST = "url_list";
    public static final String EXTRA_PAGE_INDEX = "index";
    @BindView(R.id.viewPager)
    CustomViewPager mViewPager;
    @BindView(R.id.tv_indicator)
    TextView mTvIndicator;

    private List<String> mUrls;
    private int mCurrentIndex;// 当前显示的图片索引

    @Override
    protected int createView() {
        return R.layout.activity_photo_preview;
    }

    @Override
    protected void viewCreated() {
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColor(R.color.black)
                .statusBarDarkFont(false)
                .navigationBarEnable(true)
                .navigationBarColor(R.color.black)
                .init();
        ButterKnife.bind(this);
        mUrls = getIntent().getStringArrayListExtra(EXTRA_URL_LIST);
        if (mUrls == null || mUrls.isEmpty()) {
            ToastUtils.toast("图片加载错误");
            finish();
            return;
        }
        mCurrentIndex = getIntent().getIntExtra(EXTRA_PAGE_INDEX, 0);
        mCurrentIndex = mCurrentIndex >= mUrls.size() ? 0 : mCurrentIndex;
        if (mUrls.size() > 1) {
            mTvIndicator.setVisibility(View.GONE);
        }
        PhotoPreviewAdapter adapter = new PhotoPreviewAdapter(this, mUrls);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mCurrentIndex);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTvIndicator.setText(
                        String.format(getString(R.string.page_indicator), mCurrentIndex + 1, mUrls.size()));
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_download:
                break;
        }
    }
}
