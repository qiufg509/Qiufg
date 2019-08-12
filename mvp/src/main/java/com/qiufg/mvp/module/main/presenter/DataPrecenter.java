package com.qiufg.mvp.module.main.presenter;

import com.qiufg.mvp.module.main.Main;
import com.qiufg.mvp.module.main.model.DataModel;
import com.qiufg.mvp.net.respond.ResultObject;

import java.lang.ref.WeakReference;

import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/08/12 17:54.
 * <p>
 * Desc：
 */
public class DataPrecenter {

    private Main mMain=null;
    private DataModel mModel;

    public DataPrecenter(Main main) {
        mMain = main;
        mModel=new DataModel();
    }

    public void getData(int number, int page){
        mModel.getData(number, page, new Subscriber(mMain));
    }

    private static class Subscriber implements Consumer<ResultObject> {
        private WeakReference<Main> mReference;

        Subscriber(Main main) {
            mReference = new WeakReference<>(main);
        }

        @Override
        public void accept(ResultObject data) {
    		if(mReference != null && mReference.get() != null){
                mReference.get().getDataSuccess();
            }
        }
    }
}
