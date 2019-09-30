package com.qiufg.mvp.wedget.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.qiufg.mvp.R;

/**
 * Created by fengguang.qiu on 2019/09/30 16:03.
 * <p>
 * Desc：提示对话框
 *
 * AlertDialog.with(PhotoPreviewActivity.this)
 *                     .setCancelable(true)
 *                     .setContent("标题")
 *                     .setTitle("我是提示内容！我是提示内容")
 *                     .setPositiveButton("确定", new View.OnClickListener() {
 *.                         @Override
 *                         public void onClick(View view) {
 *                             ToastUtils.toast("确定");
 *                         }
 *                     }).setNegativeButton("取消", new View.OnClickListener() {
 *.                 @Override
 *                 public void onClick(View view) {
 *                     ToastUtils.toast("取消");
 *                 }
 *             }).show();
 */
public class AlertDialog extends BaseDialog {
    private DialogParams mDialogParams;

    private AlertDialog() {
        mDialogParams = new DialogParams();
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.dialog_alert;
    }

    public static Builder with(AppCompatActivity activity) {
        return new Builder(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvDialogCommonTitle = view.findViewById(R.id.tv_dialog_common_title);
        TextView tvDialogCommonContent = view.findViewById(R.id.tv_dialog_common_content);
        TextView tvDialogCommonPositive = view.findViewById(R.id.tv_dialog_common_positive);
        TextView tvDialogCommonNegative = view.findViewById(R.id.tv_dialog_common_negative);

        //填充数据
        tvDialogCommonTitle.setText(mDialogParams.title);
        tvDialogCommonContent.setText(mDialogParams.content);
        tvDialogCommonPositive.setText(mDialogParams.positive);
        tvDialogCommonNegative.setText(mDialogParams.negative);

        //监听
        tvDialogCommonPositive.setOnClickListener(this);
        tvDialogCommonNegative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_common_positive:
                dismiss();
                if (mDialogParams.onPositiveClickListener != null) {
                    mDialogParams.onPositiveClickListener.onClick(v);
                }
                break;
            case R.id.tv_dialog_common_negative:
                dismiss();
                if (mDialogParams.onNegativeClickListener != null) {
                    mDialogParams.onNegativeClickListener.onClick(v);
                }
                break;
        }
    }

    @Override
    protected boolean setCancelable() {
        return mDialogParams.isCancelable;
    }

    public class DialogParams {
        String title;
        String positive;
        String negative;
        String content;
        boolean isCancelable = true;
        View.OnClickListener onPositiveClickListener;
        View.OnClickListener onNegativeClickListener;
    }

    public static class Builder {
        AppCompatActivity activity;
        DialogParams P;
        AlertDialog alertDialog;

        Builder(AppCompatActivity activity) {
            alertDialog = new AlertDialog();
            this.P = alertDialog.mDialogParams;
            this.activity = activity;
        }

        public Builder setTitle(String val) {
            P.title = val;
            return this;
        }

        public Builder setContent(String val) {
            P.content = val;
            return this;
        }

        public Builder setCancelable(boolean val) {
            P.isCancelable = val;
            return this;
        }

        public Builder setPositiveButton(String positive, View.OnClickListener onClickListener) {
            P.onPositiveClickListener = onClickListener;
            P.positive = positive;
            return this;
        }

        public Builder setNegativeButton(String negative, View.OnClickListener onClickListener) {
            P.onNegativeClickListener = onClickListener;
            P.negative = negative;
            return this;
        }

        public Builder setPositiveButton(@StringRes int positive, View.OnClickListener onClickListener) {
            P.onPositiveClickListener = onClickListener;
            P.positive = activity.getString(positive);
            return this;
        }

        public Builder setNegativeButton(@StringRes int negative, View.OnClickListener onClickListener) {
            P.onNegativeClickListener = onClickListener;
            P.negative = activity.getString(negative);
            return this;
        }

        public AlertDialog show() {
            alertDialog.show(activity);
            return alertDialog;
        }
    }
}