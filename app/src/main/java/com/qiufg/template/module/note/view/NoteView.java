package com.qiufg.template.module.note.view;

import com.qiufg.template.bean.GitHub;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.module.base.IView;

import java.util.List;

/**
 * Created by fengguang.qiu on 2019/10/08 10:06.
 * <p>
 * Desc：note页view接口
 */
public interface NoteView extends IView {

    void getArtSuccess(List<GitHub> gitHubs);

    void getArtFail(QiufgException e);
}
