package com.qiufg.mvp.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiufg.mvp.R;
import com.qiufg.mvp.bean.GirlsBean;

/**
 * Created by fengguang.qiu on 2019/09/16 15:55.
 * <p>
 * Desc：主页列表适配器
 */
public class HomeAdapter extends BaseQuickAdapter<GirlsBean, BaseViewHolder> {

    public HomeAdapter() {
        super(R.layout.item_home_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlsBean item) {
        helper.setText(R.id.tv_time, item.getDesc());
        Glide.with(mContext).load(item.getUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.pic_default).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into((ImageView) helper.getView(R.id.iv_photo));
    }
}
