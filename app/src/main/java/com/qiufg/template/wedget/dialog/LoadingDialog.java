package com.qiufg.template.wedget.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qiufg.template.R;

/**
 * Created by qiufg on 2019/10/25 21:57.
 * <p>
 * Desc：页面加载（网络加载）dialog
 */
public class LoadingDialog extends BaseDialog {
    private DialogParams mDialogParams;

    private LoadingDialog() {
        mDialogParams = new DialogParams();
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.dialog_loading;
    }

    public static Builder with(AppCompatActivity activity) {
        return new Builder(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (mDialogParams.isShowText || !TextUtils.isEmpty(mDialogParams.text)) {
            TextView tvLoading = view.findViewById(R.id.tv_loading);
            tvLoading.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mDialogParams.text)) {
                tvLoading.setText(mDialogParams.text);
            }
        }
    }

    @Override
    protected boolean setCancelable() {
        return mDialogParams.isCancelable;
    }

    public class DialogParams {
        String text;
        boolean isShowText = false;
        boolean isCancelable = true;
    }

    public static class Builder {
        AppCompatActivity activity;
        DialogParams P;
        LoadingDialog mLoadingDialog;

        Builder(AppCompatActivity activity) {
            mLoadingDialog = new LoadingDialog();
            this.P = mLoadingDialog.mDialogParams;
            this.activity = activity;
        }

        public Builder setCancelable(boolean val) {
            P.isCancelable = val;
            return this;
        }

        public Builder setLoadingText(String text) {
            P.text = text;
            return this;
        }

        public Builder isShowText(boolean show) {
            P.isShowText = show;
            return this;
        }

        public LoadingDialog show() {
            mLoadingDialog.show(activity);
            return mLoadingDialog;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
