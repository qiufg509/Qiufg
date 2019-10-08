package com.qiufg.template.module.note.presenter;

import com.qiufg.template.module.base.BasePresenter;
import com.qiufg.template.module.note.model.NoteModel;
import com.qiufg.template.module.note.view.NoteView;

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
