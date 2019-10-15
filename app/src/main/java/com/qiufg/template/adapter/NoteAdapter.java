package com.qiufg.template.adapter;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.promeg.pinyinhelper.Pinyin;
import com.qiufg.template.R;
import com.qiufg.template.bean.GitHub;

/**
 * Created by qiufg on 2019/10/11 11:02.
 * <p>
 * Desc：Note页面适配器
 */
public class NoteAdapter extends BaseQuickAdapter<GitHub, BaseViewHolder> {

    private int[] mColorRes;

    public NoteAdapter() {
        super(R.layout.item_note);
        mColorRes = new int[]{
                R.color.note_item_name_index_red, R.color.note_item_name_index_purple,
                R.color.note_item_name_index_white, R.color.note_item_name_index_black,
                R.color.note_item_name_index_gray
        };
    }

    @Override
    protected void convert(BaseViewHolder helper, GitHub item) {
        View tvName = helper.getView(R.id.tv_name);
        View tvIcon = helper.getView(R.id.tv_icon);
        if (TextUtils.isEmpty(item.getDownload_url())) {
            tvName.setVisibility(View.INVISIBLE);
            tvIcon.setVisibility(View.INVISIBLE);
        } else {
            String name = item.getName();
            ((TextView) tvName).setText(name);
            tvName.setVisibility(View.VISIBLE);
            tvIcon.setVisibility(View.VISIBLE);
            char firstChar = Pinyin.toPinyin(name.charAt(0)).charAt(0);
            if (firstChar >= 'a' && firstChar <= 'z') {
                firstChar -= 32;
            }
            ((TextView) tvIcon).setText(String.valueOf(firstChar));
            ((TextView) tvIcon).setTextColor(ContextCompat.getColor(mContext, mColorRes[(firstChar + 1) % 5]));
            tvIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, mColorRes[firstChar % 5])));
        }
    }
}
