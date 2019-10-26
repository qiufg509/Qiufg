package com.qiufg.template.module.welcome.presenter;

import com.qiufg.template.bus.RxSchedulers;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.welcome.model.WelcomeModel;
import com.qiufg.template.module.welcome.view.WelcomeView;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/10/08 10:18.
 * <p>
 * Desc：欢迎页Presenter
 */
public class WelcomePresenter extends BasePresenter<WelcomeView> {

    private WelcomeModel mModel;

    public WelcomePresenter() {
        mModel = new WelcomeModel();
    }

    public void countdown() {
        Disposable disposable = Observable.timer(1, TimeUnit.SECONDS)
                .compose(RxSchedulers.normalSchedulers())
                .subscribe(new CountDownSubscriber(mView), new ErrorAction() {
                    @Override
                    public void doNext(QiufgException e) {
                        mView.startAnimation();
                    }
                });
        mDisposable.add(disposable);
    }

    private static class CountDownSubscriber implements Consumer<Long> {
        private WeakReference<WelcomeView> mReference;

        CountDownSubscriber(WelcomeView view) {
            mReference = new WeakReference<>(view);
        }

        @Override
        public void accept(Long data) {
            if (mReference == null || mReference.get() == null) return;
            mReference.get().startAnimation();
        }
    }

    public void initData() {
        Disposable disposable = Observable.timer(3, TimeUnit.SECONDS)
                .compose(RxSchedulers.normalSchedulers())
                .subscribe(new InitDataSubscriber(mView), new ErrorAction() {
                    @Override
                    public void doNext(QiufgException e) {
                        mView.gotoMainActivity();
                    }
                });
        mDisposable.add(disposable);
    }

    private static class InitDataSubscriber implements Consumer<Long> {
        private WeakReference<WelcomeView> mReference;

        InitDataSubscriber(WelcomeView view) {
            mReference = new WeakReference<>(view);
        }

        @Override
        public void accept(Long data) {
            if (mReference == null || mReference.get() == null) return;
            mReference.get().gotoMainActivity();
        }
    }
}
