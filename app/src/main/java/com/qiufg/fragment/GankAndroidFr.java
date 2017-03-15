package com.qiufg.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.qiufg.R;
import com.qiufg.adapter.AndroidAdapter;
import com.qiufg.fragment.base.BasePageFragment;
import com.qiufg.model.AndroidBean;
import com.qiufg.net.NetWork;
import com.qiufg.net.convert.AndroidParser;
import com.qiufg.util.LogUtils;
import com.qiufg.util.Toast;
import com.qiufg.view.ImageSliderLayout;

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
    @BindView(R.id.slider)
    ImageSliderLayout mSlider;

    private AndroidAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean isVisibleToUser;
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

        createDeadData();

        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
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


        mSlider.setTouchEvent(new ImageSliderLayout.onInterceptTouchEvent() {
            @Override
            public void intercept(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (event.getY() <= mSlider.getHeight()) {
                            mRefreshLayout.setEnabled(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mRefreshLayout.setEnabled(true);
                        break;
                }
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && this.isVisibleToUser) {//由可见变为不可见
            mSlider.stopAutoCycle();
        } else if (isVisibleToUser) {
            mSlider.startAutoCycle();
        }
        this.isVisibleToUser = isVisibleToUser;
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

    private BaseSliderView.OnSliderClickListener onSliderClickListener = new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView baseSliderView) {
//            Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
//            intent.putExtra("url", baseSliderView.getBundle().getString("url"));
//            startActivity(intent);
        }
    };

    private void createDeadData() {
        String[] title = getResources().getStringArray(R.array.recommend_slider);
        int[] resID = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
        String[] urls = {"http://222.243.215.132/10/s/q/e/r/sqerphwhboqjcgkgphqnlpnwniisjr/hc.yinyuetai.com/3132014EF85A8CE3ADF791CC5E279802.flv?sc=504c36cdb211a59e",
                "http://hc.yinyuetai.com/uploads/videos/common/49C7013A1BAA13783C89309B89787324.flv?sc=1495ba36e19f984e",
                "http://hc.yinyuetai.com/uploads/videos/common/30130132CB5CF7E3242B959B0FA69A4D.flv?sc=6cbc0efe2cc78af8",
                "http://hc.yinyuetai.com/uploads/videos/common/88D40148B154D6E4C3D314C95E16682B.flv?sc=fda31c7df2879aa5",
                "http://hc.yinyuetai.com/uploads/videos/common/321A013F30A2C13A4A7BB694CA2C63DC.flv?sc=9bf16d2aea478c45"};
        for (int index = 0; index < urls.length; index++) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            Bundle bundle = new Bundle();
            bundle.putString("url", urls[index]);
            textSliderView.bundle(bundle)
                    .description(title[index])
                    .image(resID[index])
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(onSliderClickListener);
            mSlider.addSlider(textSliderView);
        }
    }
}
