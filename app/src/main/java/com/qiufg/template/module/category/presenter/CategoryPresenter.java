package com.qiufg.template.module.category.presenter;

import com.qiufg.template.bean.GankBean;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BaseFragment;
import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.category.model.CategoryModel;
import com.qiufg.template.module.category.view.CategoryView;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/09/18 10:39.
 * <p>
 * Desc：分类页Presenter
 */
public class CategoryPresenter extends BasePresenter<CategoryView> {

    private static final int NUMBER_PER_PAGE = 10;
    public int mPage = 1;
    private CategoryModel mModel;

    public CategoryPresenter() {
        mModel = new CategoryModel();
    }

    public List<BaseFragment> getFragments() {
        return mModel.getFragments();
    }

    private void getGankData(String type, int page) {
        Disposable disposable = mModel.getCategoryData(type, NUMBER_PER_PAGE, page, new GankSubscriber(mView), new ErrorAction() {
            @Override
            public void doNext(QiufgException e) {
                mView.getGankFail(e);
            }
        });
        mDisposable.add(disposable);
    }

    public void initData(String type) {
        mView.showLoading();
        getGankData(type, mPage);
    }

    public void refreshData(String type) {
        getGankData(type, mPage = 1);
    }

    public void loadMoreData(String type) {
        getGankData(type, ++mPage);
    }

    private static class GankSubscriber implements Consumer<List<GankBean>> {
        private WeakReference<CategoryView> mReference;

        GankSubscriber(CategoryView view) {
            mReference = new WeakReference<>(view);
        }

        @Override
        public void accept(List<GankBean> data) {
            if (mReference == null || mReference.get() == null) return;
            CategoryView view = mReference.get();
            view.getGankSuccess(data);
        }
    }
}
