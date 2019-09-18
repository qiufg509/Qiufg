package com.qiufg.mvp.module.category.model;

import com.qiufg.mvp.bean.Category;
import com.qiufg.mvp.bean.GankBean;
import com.qiufg.mvp.bus.RxSchedulers;
import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.module.base.BaseFragment;
import com.qiufg.mvp.module.category.view.GankFragment;
import com.qiufg.mvp.net.HttpClient;
import com.qiufg.mvp.net.respond.ResultArray;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/09/18 10:37.
 * <p>
 * Desc：分类页Model
 */
public class CategoryModel {

    private CategoryApi mApi;

    public CategoryModel() {
        mApi = HttpClient.getInstance().createApi(CategoryApi.class);
    }

    public List<BaseFragment> getFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        for (Category value : Category.values()) {
            GankFragment fragment = GankFragment.newInstance(value.getType());
            fragments.add(fragment);
        }
        return fragments;
    }

    public Disposable getCategoryData(String type, int number, int page, Consumer<List<GankBean>> consumer, ErrorAction error) {
        return mApi.getGankData(type, number, page)
                .map(ResultArray::getResults)
                .compose(RxSchedulers.ioSchedulers()).subscribe(consumer, error);
    }
}
