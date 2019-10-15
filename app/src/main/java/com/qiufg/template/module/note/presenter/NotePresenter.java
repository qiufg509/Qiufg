package com.qiufg.template.module.note.presenter;

import com.qiufg.template.bean.GitHub;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.note.model.NoteModel;
import com.qiufg.template.module.note.view.NoteView;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/10/08 10:08.
 * <p>
 * Desc：Note页Presenter
 */
public class NotePresenter extends BasePresenter<NoteView> {

    private NoteModel mModel;

    public NotePresenter() {
        mModel = new NoteModel();
    }

    public void getArticle() {
        mView.showLoading();
        Disposable disposable = mModel.getArticle(new ArtSubscriber(mView), new ErrorAction() {
            @Override
            public void doNext(QiufgException e) {
                mView.getArtFail(e);
            }
        });
        mDisposable.add(disposable);
    }

    private static class ArtSubscriber implements Consumer<List<GitHub>> {
        private WeakReference<NoteView> mReference;

        ArtSubscriber(NoteView view) {
            mReference = new WeakReference<>(view);
        }

        @Override
        public void accept(List<GitHub> data) {
            if (mReference == null || mReference.get() == null) return;
            mReference.get().getArtSuccess(data);
        }
    }
}
