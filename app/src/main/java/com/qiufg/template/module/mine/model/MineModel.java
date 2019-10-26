package com.qiufg.template.module.mine.model;

import com.qiufg.template.Const;
import com.qiufg.template.bean.User;
import com.qiufg.template.bus.RxSchedulers;
import com.qiufg.template.db.DBManager;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.net.HttpClient;
import com.qiufg.template.net.ServiceUrls;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/10/08 10:16.
 * <p>
 * Desc：我的页Model
 */
public class MineModel {

    private MineApi mApi;

    public MineModel() {
        mApi = HttpClient.getInstance().createApi(MineApi.class);
    }

    public Disposable getUserInfo(String user, Consumer<User> consumer, ErrorAction error) {
        return mApi.getUserInfo(String.format(ServiceUrls.API_USER, ServiceUrls.SERVER_GITHUB, user), Const.CLIENT_ID, Const.CLIENT_SECRET)
                .retry(3, throwable -> true)
                .doOnNext(data -> DBManager.getInstance().insertUser(data))
                .compose(RxSchedulers.ioSchedulers())
                .subscribe(consumer, error);
    }
}
