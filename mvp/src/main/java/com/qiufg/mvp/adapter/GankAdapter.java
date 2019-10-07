package com.qiufg.mvp.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiufg.mvp.R;
import com.qiufg.mvp.bean.GankBean;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/09/19 10:08.
 * <p>
 * Desc：分类页gank内容列表适配器
 */
public class GankAdapter extends BaseQuickAdapter<GankBean, BaseViewHolder> {

    public GankAdapter() {
        super(R.layout.item_gank);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean item) {
        helper.setText(R.id.tv_des, item.getDesc());
        helper.setText(R.id.tv_who, item.getWho());
        helper.setText(R.id.tv_publish_time, item.getPublishedAt().substring(0, 9));
        List<String> images = item.getImages();
        ImageView imageView = helper.getView(R.id.iv_sample_graph);
        if (images != null && images.size() >= 1) {
            Glide.with(mContext).load(images.get(0))
                    .apply(new RequestOptions().placeholder(R.mipmap.img_default).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }
}
