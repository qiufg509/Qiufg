package com.qiufg.fragment.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import rx.Subscription;

/**
 * Author qiufg
 * Date 2017/2/19
 */
public class BaseFragment extends Fragment {

    private ProgressDialog mDialog;
    protected Subscription mSubscription;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    /**
     * 显示一个Toast类型的消息
     *
     * @param msg 显示的消息
     */
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast mToast = Toast.makeText(getActivity() == null ? getContext() : getActivity(), msg,
                    Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);// 设置土司的显示位置：中间显示
            mToast.show();
        }
    }

    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    public void showProgressDialog(String msg, boolean isCancel) {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        Activity context = getActivity();
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(msg != null ? msg : "");
        mDialog.setCancelable(isCancel);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        mDialog.show();
    }
}
