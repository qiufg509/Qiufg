package com.qiufg.template.module.preview.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.template.App;
import com.qiufg.template.R;
import com.qiufg.template.adapter.PhotoPreviewAdapter;
import com.qiufg.template.listener.OnPhotoPreviewClickListener;
import com.qiufg.template.module.base.MVPActivity;
import com.qiufg.template.module.preview.presenter.PreviewPresenter;
import com.qiufg.template.util.ToastUtils;
import com.qiufg.template.wedget.CustomViewPager;
import com.qiufg.template.wedget.dialog.SelectDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skin.support.content.res.SkinCompatResources;

/**
 * Created by fengguang.qiu on 2019/09/26 17:49.
 * <p>
 * Desc：照片预览页
 */
public class PhotoPreviewActivity extends MVPActivity<PreviewPresenter> implements PreviewView {

    public static final String EXTRA_URL_LIST = "url_list";
    public static final String EXTRA_PAGE_INDEX = "index";
    private static final int PERMISSIONS_CODE = 1;
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
    protected PreviewPresenter initPresenter() {
        return new PreviewPresenter();
    }

    @Override
    protected void viewCreated() {
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColorInt(SkinCompatResources.getColor(App.getInstance(), R.color.photo_preview_toolbar_bg_start))
                .statusBarDarkFont(false)
                .navigationBarEnable(true)
                .navigationBarColorInt(SkinCompatResources.getColor(App.getInstance(), R.color.photo_preview_toolbar_bg_start))
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
            mTvIndicator.setVisibility(View.VISIBLE);
            mTvIndicator.setText(
                    String.format(getString(R.string.page_indicator), mCurrentIndex + 1, mUrls.size()));
        }
        PhotoPreviewAdapter adapter = new PhotoPreviewAdapter(this, mUrls, mPhotoPreviewClickListener);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mCurrentIndex);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
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
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, PERMISSIONS_CODE);
                } else {
                    mPresenter.downloadPhoto(mUrls.get(mCurrentIndex));
                }
                break;
        }
    }

    private OnPhotoPreviewClickListener mPhotoPreviewClickListener = new OnPhotoPreviewClickListener() {
        @Override
        public void onClickListener() {
            finish();
        }

        @Override
        public void onLongClickListener(String url) {
            SelectDialog.with(PhotoPreviewActivity.this)
                    .setCancelable(true)
                    .setNegativeButton(getString(R.string.photo_preview_set_lockwrapper), v -> mPresenter.setLockWrapper(url))
                    .setPositiveButton(getString(R.string.photo_preview_set_wallpaper), v -> mPresenter.setWallPaper(url)).show();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.downloadPhoto(mUrls.get(mCurrentIndex));
                } else {
                    ToastUtils.toast("您拒绝了存储权限，下载失败！");
                }
            }
        }
    }

    @Override
    public void downloadPhotoSuccess(String path) {
        ToastUtils.toast("图片已保存至相册");
    }

    @Override
    public void downloadPhotoFail() {
        ToastUtils.toast("保存图片失败");
    }

    @Override
    public void setWallpaperSuccess() {
        ToastUtils.toast("设置成功");
    }

    @Override
    public void setWallpaperFail() {
        ToastUtils.toast("设置失败");
    }

    @Override
    public void setLockWrapperSuccess() {
        ToastUtils.toast("设置成功");
    }

    @Override
    public void setLockWrapperFail() {
        ToastUtils.toast("设置失败");
    }
}
