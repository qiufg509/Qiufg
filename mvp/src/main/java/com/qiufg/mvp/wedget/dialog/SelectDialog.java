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
 * Desc：两个选项话框
 **/
public class SelectDialog extends BaseDialog {
    private DialogParams mDialogParams;

    private SelectDialog() {
        mDialogParams = new DialogParams();
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.dialog_select;
    }

    public static Builder with(AppCompatActivity activity) {
        return new Builder(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvDialogCommonPositive = view.findViewById(R.id.tv_dialog_common_positive);
        TextView tvDialogCommonNegative = view.findViewById(R.id.tv_dialog_common_negative);

        //填充数据
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
        String positive;
        String negative;
        boolean isCancelable = true;
        View.OnClickListener onPositiveClickListener;
        View.OnClickListener onNegativeClickListener;
    }

    public static class Builder {
        AppCompatActivity activity;
        DialogParams P;
        SelectDialog selectDialog;

        Builder(AppCompatActivity activity) {
            selectDialog = new SelectDialog();
            this.P = selectDialog.mDialogParams;
            this.activity = activity;
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

        public SelectDialog show() {
            selectDialog.show(activity);
            return selectDialog;
        }
    }
}