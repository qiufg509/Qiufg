package com.qiufg.template.module.mine.presenter;

import com.qiufg.template.Const;
import com.qiufg.template.bean.User;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.mine.model.MineModel;
import com.qiufg.template.module.mine.view.MineView;
import com.qiufg.template.util.SPUtils;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/10/08 10:18.
 * <p>
 * Desc：我的页Presenter
 */
public class MinePresenter extends BasePresenter<MineView> {

    private MineModel mModel;

    public MinePresenter() {
        mModel = new MineModel();
    }

    public void getUserInfo() {
        String user = SPUtils.getString("user", Const.USER);
        Disposable disposable = mModel.getUserInfo(user, new UserSubscriber(mView), new ErrorAction() {
            @Override
            public void doNext(QiufgException e) {
                mView.getUserFail(e);
            }
        });
        mDisposable.add(disposable);
    }

    private static class UserSubscriber implements Consumer<User> {
        private WeakReference<MineView> mReference;

        UserSubscriber(MineView view) {
            mReference = new WeakReference<>(view);
        }

        @Override
        public void accept(User data) {
            if (mReference == null || mReference.get() == null) return;
            mReference.get().getUserSuccess(data);
        }
    }
}
