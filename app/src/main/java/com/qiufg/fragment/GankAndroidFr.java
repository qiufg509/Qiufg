package com.qiufg.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiufg.R;
import com.qiufg.adapter.AndroidAdapter;
import com.qiufg.fragment.base.BasePageFragment;
import com.qiufg.model.AndroidBean;
import com.qiufg.network.NetWork;
import com.qiufg.network.convert.AndroidParser;
import com.qiufg.util.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Description
 * Author qiufg
 * Date 2017/2/20
 */
public class GankAndroidFr extends BasePageFragment {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private AndroidAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int page = 1;


    public GankAndroidFr() {
        // Required empty public constructor
    }

    public static GankAndroidFr newInstance() {
        return new GankAndroidFr();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_gank_android, container, false);
        ButterKnife.bind(this, view);

        init();

        initListener();
        return view;
    }

    private void init() {
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        adapter = new AndroidAdapter(getActivity());
        mRecycler.setAdapter(adapter);

        mRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        24, getResources().getDisplayMetrics()));
        mRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
    }

    private void initListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadPage(page);
            }
        });

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisualItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisualItemPosition + 2 >= layoutManager.getItemCount()
                        && !mRefreshLayout.isRefreshing()) {
                    loadPage(++page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisualItemPosition = layoutManager.findLastVisibleItemPosition();

            }
        });
    }

    @Override
    public void fetchData() {
        loadPage(page);
    }

    private void loadPage(int page) {
        mSubscription = NetWork.getAndroidListApi()
                .getAndroidData(10, page)
                .map(new AndroidParser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);

    }

    Observer<List<AndroidBean>> mObserver = new Observer<List<AndroidBean>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            mRefreshLayout.setRefreshing(false);
            Toast.show(getContext(), "数据加载失败");

        }

        @Override
        public void onNext(List<AndroidBean> androidBeen) {
            if (page > 1) {
                if (androidBeen != null && !androidBeen.isEmpty()) {
                    adapter.addData(androidBeen);
                } else {
                    Toast.show(getContext(), "数据加载完全");
                }
            } else {
                adapter.setData(androidBeen);
            }
            mRefreshLayout.setRefreshing(false);

        }
    };
}
