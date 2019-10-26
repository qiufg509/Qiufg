package com.qiufg.template.wedget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.qiufg.template.R;

/**
 * Created by qiufg on 2019/10/26 10:54.
 * <p>
 * Desc：我的页自定义Item
 */
public class MineItemView extends FrameLayout {

    public MineItemView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public MineItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MineItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MineItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_mine, this);
        TextView tvName = v.findViewById(R.id.tv_name);
        ImageView ivIcon = v.findViewById(R.id.iv_icon);
        ImageView ivArrow = v.findViewById(R.id.iv_arrow);
        TypedArray ta = null;
        Drawable icon;
        String name;
        int nameColor;
        boolean showArrow;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.MineItemView);
            icon = ta.getDrawable(R.styleable.MineItemView_MIV_Icon);
            name = ta.getString(R.styleable.MineItemView_MIV_Text);
            nameColor = ta.getColor(R.styleable.MineItemView_MIV_Text_Color, ContextCompat.getColor(context, R.color.mine_text));
            showArrow = ta.getBoolean(R.styleable.MineItemView_MIV_Show_Arrow, true);
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }

        tvName.setText(name);
        tvName.setTextColor(nameColor);
        ivIcon.setImageDrawable(icon);
        ivArrow.setVisibility(showArrow ? VISIBLE : GONE);
    }

}
