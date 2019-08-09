package com.qiufg.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.qiufg.Constants;
import com.qiufg.R;
import com.qiufg.adapter.PagerAdapter;
import com.qiufg.events.ModulesCallBack;
import com.qiufg.fragment.CommandFr;
import com.qiufg.fragment.GankAndroidFr;
import com.qiufg.fragment.HomeFr;
import com.qiufg.fragment.QrScanFr;
import com.qiufg.fragment.base.BasePageFragment;
import com.qiufg.service.AIDLService;
import com.qiufg.util.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.qiufg.R.id.fab;
import static com.qiufg.R.id.toolbar;

/**
 * mViewPager.setOffscreenPageLimit()//这个方法是用来控制fragment不重新走生命周期的个数的，打个比方一共4个fragment页面，如果mViewPager.setOffscreenPageLimit(3)，那么所有的fragment都只走一次生命周期，如果是mViewPager.setOffscreenPageLimit(2)，那么其中有一个fragment会在切换的时候重新走一遍生命周期，FragmentStatePagerAdapter和FragmentPagerAdapter都是这样，但是FragmentPagerAdapter设置setOffscreenPageLimit不影响fragment缓存的个数,而FragmentStatePagerAdapter缓存的fragment实例个数就是setOffscreenPageLimit设置的值+1
 */
@RuntimePermissions
public class MainAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ModulesCallBack {

    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(fab)
    FloatingActionButton mFab;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        initListener();

        initViewpager();
        MainActPermissionsDispatcher.checkPermissionsWithCheck(this);
        checkPermissions();
//        startService(new Intent(this, AIDLService.class));
        jump();
    }

    private void initListener() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        mFab.setOnClickListener(view -> {
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            int screenWidth = display.getWidth();
            int screenHeight = display.getHeight();

            // 方法2
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            float width = dm.widthPixels * dm.density;
            float height = dm.heightPixels * dm.density;

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int screenWidth2 = displayMetrics.widthPixels;
            int screenHeight2 = displayMetrics.heightPixels;

            double b
                    = screenWidth + screenHeight
                    + width + height
                    + screenWidth2 + screenHeight2;

            Snackbar.make(view, "Replace with your own action" + b, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }

    private void initViewpager() {

        HomeFr homeFr = HomeFr.newInstance("test");
        QrScanFr qrScanFr = QrScanFr.newInstance();
        CommandFr commandFr = CommandFr.newInstance();
        GankAndroidFr gankAndroidFr = GankAndroidFr.newInstance();
        List<BasePageFragment> list = new ArrayList<>();
        list.add(homeFr);
        list.add(qrScanFr);
        list.add(commandFr);
        list.add(gankAndroidFr);

        PagerAdapter pagerAdapter = new PagerAdapter<>(getSupportFragmentManager(), list);

        mViewpager.setOffscreenPageLimit(3);//用来控制Fragment不重走生命周期的方法
        mViewpager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 优雅退出APP方式（主界面设置启动模式singleTask）
     * Intent exit=new Intent(this,MainAct.class);
     * exit.putExtra(Constants.EXTRA_APP_EXIT,true);
     * startActivity(exit);
     *
     * @param intent 传参true即退出
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null && intent.getBooleanExtra(Constants.EXTRA_APP_EXIT, false)) {
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, ServiceAct.class);
            Bundle bundle = new Bundle();
            bundle.putString("money", "55100.33");
            intent.putExtra(Constants.EXTRA_SERVICE_ACT, bundle);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, DownloadAct.class));

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this, HotfixAct.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(this, AnimAct.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, AlarmAct.class));
        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void toggleActiveFragment(int btnID, String... args) {

    }

    private void jump() {
        Bundle bundle = getIntent().getBundleExtra(Constants.EXTRA_SERVICE_ACT);
        if (bundle != null) {
            switch (bundle.getInt(Constants.EXTRA_DESTINATION, 0)) {
                case 1:
                    Intent serviceActIntent = new Intent(this, ServiceAct.class);
                    serviceActIntent.putExtra(Constants.EXTRA_SERVICE_ACT, bundle);
                    startActivity(serviceActIntent);
                    break;
                case 2:
                    Intent alarmActIntent = new Intent(this, ServiceAct.class);
                    alarmActIntent.putExtra(Constants.EXTRA_SERVICE_ACT, bundle);
                    startActivity(alarmActIntent);
                    break;
            }
        }
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
    void checkPermissions() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
    void onRationalePermission(PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
    void onDeniedPermission() {
        Toast.show(this, "禁止");
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
    void onNeverAskPermission() {
        Toast.show(this, "不再提示");
    }
}
