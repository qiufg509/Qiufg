package com.qiufg.template.wedget.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Created by fengguang.qiu on 2019/09/30 15:41.
 * <p>
 * Desc：对话框基类
 */
public class BaseDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private DialogParams dialogParams;

    public BaseDialog() {
        this.dialogParams = new DialogParams();
    }

    private void show(AppCompatActivity activity, String tag) {
        if (!TextUtils.isEmpty(tag)) {
            show(activity.getSupportFragmentManager(), tag);
        } else {
            show(activity);
        }
    }

    public void show(AppCompatActivity activity) {
        show(activity.getSupportFragmentManager(), activity.getClass().getSimpleName());
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //getDialog().setCancelable(setCancelable());
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(setCancelable());
            setCancelable(setCancelable());
            //设置背景透明
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected boolean setCancelable() {
        return dialogParams.isCancelable;
    }

    @NonNull
    @Override
    public final android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getContext() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogLayout = LayoutInflater.from(getContext()).inflate(setLayoutRes(), null);
        builder.setView(dialogLayout);
        onViewCreated(dialogLayout, null);
        return builder.create();
    }

    protected int setLayoutRes() {
        return dialogParams.contentView;
    }

    @Override
    public void onClick(View v) {

    }

    public class DialogParams {
        AppCompatActivity activity;
        int style;
        int animations;
        int contentView;
        String tag;
        boolean isCancelable;
    }

    public static class Builder {
        private DialogParams P;
        private BaseDialog dialog;

        private Builder(AppCompatActivity activity) {
            dialog = new BaseDialog();
            P = dialog.dialogParams;
            P.activity = activity;
        }

        public Builder setStyle(int val) {
            P.style = val;
            return this;
        }

        public Builder setAnimations(int val) {
            P.animations = val;
            return this;
        }

        public Builder setContentView(@LayoutRes int val) {
            P.contentView = val;
            return this;
        }

        public Builder setCancelable(boolean val) {
            P.isCancelable = val;
            return this;
        }

        public BaseDialog build() {
            if (P.contentView == -1) {
                throw new IllegalArgumentException("Please set setContentView");
            }
            dialog.show(P.activity, P.tag);
            return dialog;
        }
    }
}