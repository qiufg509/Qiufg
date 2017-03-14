package com.qiufg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GankAndroidFr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GankAndroidFr extends BasePageFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private String mParam1;
    private String mParam2;
    private AndroidAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int page = 1;


    public GankAndroidFr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GankAndroidFr.
     */
    public static GankAndroidFr newInstance(String param1, String param2) {
        GankAndroidFr fragment = new GankAndroidFr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            showToast("数据加载失败");

        }

        @Override
        public void onNext(List<AndroidBean> androidBeen) {
            if (page > 1) {
                if (androidBeen != null && !androidBeen.isEmpty()) {
                    adapter.addData(androidBeen);
                } else {
                    showToast("数据加载完全");
                }
            } else {
                adapter.setData(androidBeen);
            }
            mRefreshLayout.setRefreshing(false);

        }
    };
}
