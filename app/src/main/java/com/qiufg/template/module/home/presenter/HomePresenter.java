package com.qiufg.template.module.home.presenter;

import com.qiufg.template.bean.GirlsBean;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.home.model.HomeModel;
import com.qiufg.template.module.home.view.HomeView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/08/12 17:54.
 * <p>
 * Desc：首页（美女们）Presenter
 */
public class HomePresenter extends BasePresenter<HomeView> {

    private static final int NUMBER_PER_PAGE = 10;
    private static final String TYPE = "福利";
    public int mPage = 1;
    private HomeModel mModel;

    public HomePresenter() {
        mModel = new HomeModel();
    }

    private void getBannerData() {
        Disposable disposable = mModel.getBannerData(new BannerSubscriber(mView), new ErrorAction() {
            @Override
            public void doNext(QiufgException e) {
                List<String> placeholder = new ArrayList<>();
                placeholder.add("http://");
                mView.setBannerImages(placeholder);
            }
        });
        mDisposable.add(disposable);
    }

    private void getGirlData(int page) {
        Disposable disposable = mModel.getGirlData(TYPE, NUMBER_PER_PAGE, page, new GirlsSubscriber(mView), new ErrorAction() {
            @Override
            public void doNext(QiufgException e) {
                mView.getGirlsFail(e);
            }
        });
        mDisposable.add(disposable);
    }

    public void initData() {
        mView.showLoading();
        getBannerData();
        getGirlData(mPage);
    }

    public void refreshData() {
        getBannerData();
        getGirlData(mPage = 1);
    }

    public void loadMoreData() {
        getGirlData(++mPage);
    }

    private static class BannerSubscriber implements Consumer<List<String>> {
        private WeakReference<HomeView> mReference;

        BannerSubscriber(HomeView homeView) {
            mReference = new WeakReference<>(homeView);
        }

        @Override
        public void accept(List<String> data) {
            if (mReference == null || mReference.get() == null) return;
            HomeView homeView = mReference.get();
            homeView.setBannerImages(data);
        }
    }

    private static class GirlsSubscriber implements Consumer<List<GirlsBean>> {
        private WeakReference<HomeView> mReference;

        GirlsSubscriber(HomeView homeView) {
            mReference = new WeakReference<>(homeView);
        }

        @Override
        public void accept(List<GirlsBean> data) {
            if (mReference != null && mReference.get() != null) {
                HomeView view = mReference.get();
                view.getGirlsSuccess(data);
            }
        }
    }
}
