package com.qiufg.mvp.module.note.presenter;

import com.qiufg.mvp.module.base.BasePresenter;
import com.qiufg.mvp.module.note.model.NoteModel;
import com.qiufg.mvp.module.note.view.NoteView;

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
}
