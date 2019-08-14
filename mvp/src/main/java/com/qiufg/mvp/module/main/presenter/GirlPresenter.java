package com.qiufg.mvp.module.main.presenter;

import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.exception.ForestException;
import com.qiufg.mvp.module.base.IPresenter;
import com.qiufg.mvp.module.base.IView;
import com.qiufg.mvp.module.main.view.GirlView;
import com.qiufg.mvp.module.main.model.GirlModel;
import com.qiufg.mvp.module.main.model.GirlsBean;
import com.qiufg.mvp.net.respond.ResultArray;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/08/12 17:54.
 * <p>
 * Desc：
 */
public class GirlPresenter implements IPresenter {

    private GirlView mView;
    private GirlModel mModel;
    private CompositeDisposable mDisposable;

    public GirlPresenter(GirlView view) {
        mModel = new GirlModel();
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void attach(IView view) {
    }

    @Override
    public void detach() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mView=null;
    }

    public void getData(int number, int page) {
        Disposable disposable = mModel.getData(number, page, new Subscriber(mView), new ErrorAction() {
            @Override
            public void doNext(ForestException e) {
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
                mReference.get().getDataSuccess();
            }
        }
    }
}
