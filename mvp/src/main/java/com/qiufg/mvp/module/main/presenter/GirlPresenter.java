package com.qiufg.mvp.module.main.presenter;

import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.exception.ForestException;
import com.qiufg.mvp.module.base.BasePresenter;
import com.qiufg.mvp.module.main.model.GirlModel;
import com.qiufg.mvp.module.main.view.GirlView;
import com.qiufg.mvp.net.respond.ResultArray;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/08/12 17:54.
 * <p>
 * Desc：
 */
public class GirlPresenter extends BasePresenter<GirlView> {

    private GirlModel mModel;

    public GirlPresenter() {
        mModel = new GirlModel();
    }

    public void getData(int number, int page) {
        mView.showLoading();
        Disposable disposable = mModel.getData(number, page, new Subscriber(mView), new ErrorAction() {
            @Override
            public void doNext(ForestException e) {
                mView.hideLoading();
                mView.getDataFail();
            }
        });
        mDisposable.add(disposable);
    }

    private static class Subscriber implements Consumer<ResultArray<GirlsBean>> {
        private WeakReference<GirlView> mReference;

        Subscriber(GirlView girlView) {
            mReference = new WeakReference<>(girlView);
        }

        @Override
        public void accept(ResultArray<GirlsBean> data) {
            if (mReference != null && mReference.get() != null) {
                GirlView view = mReference.get();
                view.getDataSuccess();
                view.hideLoading();
            }
        }
    }
}
