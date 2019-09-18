package com.qiufg.mvp.module.home.presenter;

import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.exception.ForestException;
import com.qiufg.mvp.module.base.BasePresenter;
import com.qiufg.mvp.module.home.model.HomeModel;
import com.qiufg.mvp.module.home.view.HomeView;

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
    private HomeModel mModel;

    public HomePresenter() {
        mModel = new HomeModel();
    }

    private void getBannerData() {
        Disposable disposable = mModel.getBannerData(new BannerSubscriber(mView), new ErrorAction() {
            @Override
            public void doNext(ForestException e) {
                mView.setBannerImages(new ArrayList<>());
            }
        });
        mDisposable.add(disposable);
    }

    public void getGirlData(int page) {
        Disposable disposable = mModel.getGirlData(TYPE, NUMBER_PER_PAGE, page, new GirlsSubscriber(mView), new ErrorAction() {
            @Override
            public void doNext(ForestException e) {
                mView.getGirlsFail();
            }
        });
        mDisposable.add(disposable);
    }

    public void getData(int page) {
        mView.showLoading();
        getBannerData();
        getGirlData(page);
    }

    public void refreshData(int page) {
        getBannerData();
        getGirlData(page);
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
