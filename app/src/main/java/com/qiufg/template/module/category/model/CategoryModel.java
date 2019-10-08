package com.qiufg.template.module.category.model;

import com.qiufg.template.bean.Category;
import com.qiufg.template.bean.GankBean;
import com.qiufg.template.bus.RxSchedulers;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.exception.QiufgCode;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.category.view.GankFragment;
import com.qiufg.template.net.HttpClient;

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
                .map(gankBeanResultArray -> {
                    List<GankBean> results = gankBeanResultArray.getResults();
                    if (results.isEmpty()) {
                        throw new QiufgException(QiufgCode.CODE_DATA_EMPTY);
                    }
                    return results;
                })
                .compose(RxSchedulers.ioSchedulers()).subscribe(consumer, error);
    }
}
