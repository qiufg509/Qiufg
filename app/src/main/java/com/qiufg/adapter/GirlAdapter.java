package com.qiufg.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qiufg.R;
import com.qiufg.model.GirlBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author qiufg
 * Date 2017/2/20
 */

public class GirlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private List<GirlBean> mGirlBeens;
    private OnRecyclerViewItemClickListener mClickListener;

    public GirlAdapter(Context context, OnRecyclerViewItemClickListener mClickListener) {
        this.mContext = context;
        this.mClickListener = mClickListener;
    }

    public void setData(List<GirlBean> girlBeens) {
        this.mGirlBeens = girlBeens;
        notifyDataSetChanged();
    }

    public void addData(List<GirlBean> girlBeens) {
        if (this.mGirlBeens != null) {
            this.mGirlBeens.addAll(girlBeens);
        } else {
            this.mGirlBeens = girlBeens;
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card_girl, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).setImage(mGirlBeens.get(position).getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return mGirlBeens == null ? 0 : mGirlBeens.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView mImage;


        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setImage(String url) {
            Glide.with(mContext).load(url).into(this.mImage);//加载网络图片
        }
    }

    //自定义监听事件
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);

        boolean onItemLongClick(View view);
    }

    @Override
    public void onClick(View view) {
        mClickListener.onItemClick(view);
    }

    @Override
    public boolean onLongClick(View view) {
        return mClickListener.onItemLongClick(view);
    }
}
