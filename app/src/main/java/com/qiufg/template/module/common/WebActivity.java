package com.qiufg.template.module.common;

import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.template.App;
import com.qiufg.template.R;
import com.qiufg.template.module.base.BaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skin.support.content.res.SkinCompatResources;

/**
 * Created by fengguang.qiu on 2019/09/25 10:50.
 * <p>
 * Desc：web展示页
 */
public class WebActivity extends BaseActivity {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.root_view)
    LinearLayout mRootView;
    private WebView mWebView;

    @Override
    protected int createView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void viewCreated() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .statusBarColor(SkinCompatResources.getColor(App.getInstance(), R.color.colorPrimary))
                .init();
        ButterKnife.bind(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mRootView.addView(mWebView);

        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        WebSettings settings = this.mWebView.getSettings();
        settings.setAllowFileAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setJavaScriptEnabled(false);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath());
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        this.mWebView.setWebChromeClient(new ChromeClient(this));
        this.mWebView.setWebViewClient(new WebClient());
        this.mWebView.loadUrl(url);
        if (title != null) {
            mTvTitle.setText(title);
        }

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (this.mWebView.canGoBack()) {
                    this.mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onPause() {
        if (this.mWebView != null) {
            this.mWebView.onPause();
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (this.mWebView != null) {
            this.mWebView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    private static class ChromeClient extends WebChromeClient {
        private WeakReference<WebActivity> mReference;

        ChromeClient(WebActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (mReference == null || mReference.get() == null) return;
            WebActivity activity = mReference.get();
//            activity.mProgressbar.setProgress(i);
//            activity.mProgressbar.setVisibility((i >= 100) ? View.GONE : View.VISIBLE);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mReference == null || mReference.get() == null) return;
            mReference.get().mTvTitle.setText(title);
        }
    }

    private static class WebClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str != null) {
                webView.loadUrl(str);
            }
            return true;
        }
    }
}
