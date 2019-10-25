package com.qiufg.template.module.welcome.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.template.R;
import com.qiufg.template.module.base.MVPActivity;
import com.qiufg.template.module.main.view.MainActivity;
import com.qiufg.template.module.welcome.presenter.WelcomePresenter;
import com.qiufg.template.util.DisplayUtils;

import butterknife.BindView;

/**
 * Created by qiufg on 2019/10/25 13:41.
 * <p>
 * Desc：欢迎页
 */
public class WelcomeActivity extends MVPActivity<WelcomePresenter> implements WelcomeView {

    @BindView(R.id.iv_logo)
    ImageView mIvLogo;

    @Override
    protected WelcomePresenter initPresenter() {
        return new WelcomePresenter();
    }

    @Override
    protected int createView() {
        getWindow().setBackgroundDrawable(null);
        return R.layout.activity_welcome;
    }

    @Override
    protected void viewCreated() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(false).init();
        mPresenter.countdown();
        mPresenter.initData();
    }

    @Override
    public void gotoMainActivity() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void startAnimation() {
        int imgHeight = mIvLogo.getHeight() / 5;
        int imgWidth = mIvLogo.getWidth() / 2;
        int height = DisplayUtils.getWindowHeight();
        int Width = DisplayUtils.getWindowWidth();
        int dy = (height - imgHeight) / 2;
        int dx = (Width - imgWidth) / 2;
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(mIvLogo, "translationY", 0, dy);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(mIvLogo, "translationX", 0, dx);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(mIvLogo, "ScaleX", 1f, 0.3f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(mIvLogo, "ScaleY", 1f, 0.3f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(mIvLogo, "alpha", 1f, 0.5f);
        set.play(animatorTranslateY).with(animatorTranslateX)
                .with(animatorScaleX).with(animatorScaleY).with(animatorAlpha);
        set.setDuration(2000);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
