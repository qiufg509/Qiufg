package com.qiufg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiufg.R;
import com.qiufg.adapter.GirlAdapter;
import com.qiufg.fragment.base.BasePageFragment;
import com.qiufg.model.GirlBean;
import com.qiufg.net.NetWork;
import com.qiufg.net.convert.GirlParser;
import com.qiufg.util.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author qiufg
 * Date 2017/2/19
 */

public class HomeFr extends BasePageFragment {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.grid_swipe_refresh)
    SwipeRefreshLayout mGridSwipeRefresh;
    private GridLayoutManager layoutManager;
    private GirlAdapter adapter;

    private int page = 1;

    public HomeFr() {
    }

    public static HomeFr newInstance(String arg) {
        HomeFr homeFr = new HomeFr();
        Bundle data = new Bundle();
        data.putString("key", arg);
        homeFr.setArguments(data);
        return homeFr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_home, container, false);

        ButterKnife.bind(this, view);

        init();

        initListener();
        return view;
    }

    private void init() {
        layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new GirlAdapter(getActivity(), mItemClickListener);
        mRecycler.setAdapter(adapter);

        //调整SwipeRefreshLayout的位置
        mGridSwipeRefresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        24, getResources().getDisplayMetrics()));
        mGridSwipeRefresh.setColorSchemeResources(
                R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
    }

    @Override
    public void fetchData() {
        loadPage(page);
    }

    private GirlAdapter.OnRecyclerViewItemClickListener mItemClickListener = new GirlAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view) {

        }

        @Override
        public boolean onItemLongClick(View view) {
            return false;
        }
    };

    private void initListener() {
        mGridSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadPage(page);
            }
        });
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余少于两个item时，自动加载下一页
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && layoutManager.findLastVisibleItemPosition() + 2 >= layoutManager.getItemCount()
                        && !mGridSwipeRefresh.isRefreshing()) {
                    loadPage(++page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                int[] positions= mLayoutManager.findLastVisibleItemPositions(null);//瀑布流布局 StaggeredLayout使用
//                lastVisibleItemPosition = Math.max(positions[0],positions[1]);
            }
        });
    }

    public void loadPage(int page) {
        mGridSwipeRefresh.setRefreshing(true);
        mSubscription = NetWork.getGirlListApi()
                .getGirls(10, page)
                .map(new GirlParser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    Observer<List<GirlBean>> mObserver = new Observer<List<GirlBean>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            mGridSwipeRefresh.setRefreshing(false);
            Toast.show(getContext(), "数据加载失败");
        }

        @Override
        public void onNext(List<GirlBean> girlBeens) {
            if (page > 1) {
                if (girlBeens == null || girlBeens.isEmpty()) {
                    Toast.show(getContext(), "数据已经加载完全");
                } else {
                    adapter.addData(girlBeens);
                }
            } else {
                adapter.setData(girlBeens);
            }
            mGridSwipeRefresh.setRefreshing(false);
        }
    };
}
