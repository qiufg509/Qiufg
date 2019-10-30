package com.qiufg.template.module.common;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.server.IConversion;
import com.qiufg.server.IEmailListener;
import com.qiufg.server.IEmailManager;
import com.qiufg.server.ISecurityCenter;
import com.qiufg.server.ISort;
import com.qiufg.template.App;
import com.qiufg.template.R;
import com.qiufg.template.bean.EmailBean;
import com.qiufg.template.bus.BinderPool;
import com.qiufg.template.listener.OnServiceConnectionListener;
import com.qiufg.template.module.base.BaseActivity;
import com.qiufg.template.util.Logger;
import com.qiufg.template.util.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.content.res.SkinCompatResources;

/**
 * Created by qiufg on 2019/10/28 15:10.
 * <p>
 * Desc：AIDL使用
 */
public class AIDLActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "title";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.et_id)
    EditText mEtId;
    @BindView(R.id.et_from)
    EditText mEtFrom;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_received)
    TextView mTvReceived;
    @BindView(R.id.et_sort0)
    EditText mEtSort0;
    @BindView(R.id.et_sort1)
    EditText mEtSort1;
    @BindView(R.id.et_sort2)
    EditText mEtSort2;
    @BindView(R.id.et_sort3)
    EditText mEtSort3;
    @BindView(R.id.et_sort4)
    EditText mEtSort4;
    @BindView(R.id.tv_sort)
    TextView mTvSort;
    @BindView(R.id.et_message)
    EditText mEtMessage;
    @BindView(R.id.tv_crypt)
    TextView mTvCrypt;
    private int mType = 0;
    private IEmailManager mEmailManager;
    private OnEmailListener mEmailListener;

    @Override
    protected int createView() {
        return R.layout.activity_aidl;
    }

    @Override
    protected void viewCreated() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .statusBarColorInt(SkinCompatResources.getColor(App.getInstance(), R.color.colorPrimary))
                .init();
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (title != null) {
            mTvTitle.setText(title);
        }
        BinderPool.getBinderPool(this);
        initListener();
    }

    private void initListener() {
        mEmailListener = new OnEmailListener(mTvReceived);
        mEtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    double aDouble = Double.parseDouble(s.toString());
                    BinderPool binderPool = BinderPool.getBinderPool(AIDLActivity.this);
                    IBinder binder = binderPool.queryBinder(BinderPool.BINDER_CONVERSION);
                    IConversion asInterface = IConversion.Stub.asInterface(binder);
                    String money = asInterface.switchMoney(aDouble);
                    mTvMoney.setText(money);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        try {
            BinderPool binderPool = BinderPool.getBinderPool(AIDLActivity.this);
            IBinder binder = binderPool.queryBinder(BinderPool.BINDER_EMAIL_MANAGER);
            mEmailManager = IEmailManager.Stub.asInterface(binder);
            if (mEmailManager != null && mEmailManager.asBinder().isBinderAlive()) {
                mEmailManager.registerListener(mEmailListener);
            } else {
                binderPool.setOnServiceConnectionListener(mOnServiceConnectionListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private OnServiceConnectionListener mOnServiceConnectionListener = new OnServiceConnectionListener() {
        @Override
        public void onConnected() {
            try {
                checkEmailManager();
                mEmailManager.registerListener(mEmailListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private void checkEmailManager() {
        if (mEmailManager != null && mEmailManager.asBinder().isBinderAlive()) return;
        BinderPool binderPool = BinderPool.getBinderPool(AIDLActivity.this);
        IBinder binder = binderPool.queryBinder(BinderPool.BINDER_EMAIL_MANAGER);
        mEmailManager = IEmailManager.Stub.asInterface(binder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEtMoney.addTextChangedListener(null);
        try {
            checkEmailManager();
            mEmailManager.unregisterListener(mEmailListener);
            mEmailListener.clear();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        BinderPool.getBinderPool(AIDLActivity.this).setOnServiceConnectionListener(null);
    }

    @OnClick({R.id.iv_back, R.id.tv_add, R.id.tv_get, R.id.tv_sort, R.id.tv_crypt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add:
                sendEmail();
                break;
            case R.id.tv_get:
                getEmails();
                break;
            case R.id.tv_sort:
                sort();
                break;
            case R.id.tv_crypt:
                crypt();
                break;
        }
    }

    private void sendEmail() {
        String idStr = mEtId.getText().toString();
        String from = mEtFrom.getText().toString();
        String contentStr = mEtContent.getText().toString();
        if (TextUtils.isEmpty(idStr)) {
            ToastUtils.toast("请设置ID");
            return;
        }
        if (TextUtils.isEmpty(from)) {
            ToastUtils.toast("请设置From");
            return;
        }
        if (TextUtils.isEmpty(contentStr)) {
            ToastUtils.toast("请设置Content");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            EmailBean emailBean = new EmailBean();
            emailBean.setId(id);
            emailBean.setFrom(from);
            emailBean.setContent(contentStr);
            checkEmailManager();
            mEmailManager.sendEmail(emailBean);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void getEmails() {
        try {
            checkEmailManager();
            List<EmailBean> allEmails = mEmailManager.getAllEmails();
            if (allEmails == null || allEmails.isEmpty()) {
                ToastUtils.toast("[]");
                return;
            }
            StringBuilder sb = new StringBuilder("[");
            for (EmailBean aidlBean : allEmails) {
                sb.append("\n").append(aidlBean.toString()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n]");
            ToastUtils.toast(sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sort() {
        String s0 = mEtSort0.getText().toString();
        String s1 = mEtSort1.getText().toString();
        String s2 = mEtSort2.getText().toString();
        String s3 = mEtSort3.getText().toString();
        String s4 = mEtSort4.getText().toString();
        if (TextUtils.isEmpty(s0) || TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)
                || TextUtils.isEmpty(s3) || TextUtils.isEmpty(s4)) {
            ToastUtils.toast("请输入要排序的5个数");
            return;
        }
        try {
            BinderPool binderPool = BinderPool.getBinderPool(AIDLActivity.this);
            IBinder binder = binderPool.queryBinder(BinderPool.BINDER_SORT);
            ISort iSort = ISort.Stub.asInterface(binder);
            if (iSort == null) return;
            int[] array = new int[]{Integer.valueOf(s0), Integer.valueOf(s1),
                    Integer.valueOf(s2), Integer.valueOf(s3), Integer.valueOf(s4)};
            int[] sort = iSort.sort(mType++ % 3, array);
            mTvSort.setText(mType % 3 == 0 ? "选择排序" : (mType == 1 ? "冒泡排序" : "快速排序"));
            ToastUtils.toast(String.format(Locale.CHINESE, "排序结果：[%1d,%2d,%3d,%4d,%5d]",
                    sort[0], sort[1], sort[2], sort[3], sort[4]));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void crypt() {
        try {
            BinderPool binderPool = BinderPool.getBinderPool(AIDLActivity.this);
            IBinder binder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
            ISecurityCenter iSecurityCenter = ISecurityCenter.Stub.asInterface(binder);
            String text = mEtMessage.getText().toString();
            if (mEtMessage.isEnabled()) {
                mEtMessage.setEnabled(false);
                String encrypt = iSecurityCenter.encrypt(text);
                mTvCrypt.setText(getString(R.string.aidl_input_aidl_decrypt_hint));
                mEtMessage.setText(encrypt);
            } else {
                String decrypt = iSecurityCenter.decrypt(text);
                mTvCrypt.setText(getString(R.string.aidl_input_aidl_encrypt_hint));
                mEtMessage.setText(decrypt);
                mEtMessage.setEnabled(true);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class OnEmailListener extends IEmailListener.Stub {

        private WeakReference<TextView> mReference;

        OnEmailListener(TextView view) {
            mReference = new WeakReference<>(view);
        }

        @Override
        public void onEmailReceived(EmailBean email) {
            if (mReference == null || mReference.get() == null) {
                Logger.d("email = " + email.toString());
                return;
            }
            mReference.get().setText(email.toString());
        }

        void clear() {
            if (mReference != null) {
                mReference.clear();
            }
        }
    }

    public static void gotoAIDLActivity(FragmentActivity activity, String title) {
        Intent intent = new Intent(activity, AIDLActivity.class);
        intent.putExtra(AIDLActivity.EXTRA_TITLE, title);
        activity.startActivity(intent);
    }
}
