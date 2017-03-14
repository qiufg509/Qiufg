package com.qiufg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiufg.R;
import com.qiufg.model.AndroidBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author qiufg
 * Date 2017/3/6
 */

public class AndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<AndroidBean> mAndroidBeens;

    public AndroidAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<AndroidBean> androidBeens) {
        mAndroidBeens = androidBeens;
        notifyDataSetChanged();
    }

    public void addData(List<AndroidBean> androidBeens) {
        if (mAndroidBeens != null) {
            mAndroidBeens.addAll(androidBeens);
        } else {
            mAndroidBeens = androidBeens;
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_android, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        AndroidBean bean = mAndroidBeens.get(position);
        viewHolder.mTitle.setText(bean.getDesc());
        viewHolder.mTime.setText(bean.getCreatedAt());
        viewHolder.mWho.setText(bean.getWho());
    }

    @Override
    public int getItemCount() {
        return mAndroidBeens == null ? 0 : mAndroidBeens.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.zan)
        ImageView mZan;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.who)
        TextView mWho;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
