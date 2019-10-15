package com.qiufg.template.module.note.model;

import com.qiufg.template.Const;
import com.qiufg.template.bean.GitHub;
import com.qiufg.template.bus.RxSchedulers;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.net.HttpClient;
import com.qiufg.template.net.ServiceUrls;
import com.qiufg.template.wedget.decoration.NoteDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by fengguang.qiu on 2019/10/08 10:07.
 * <p>
 * Desc：Note页Model
 */
public class NoteModel {

    private NoteApi mApi;

    public NoteModel() {
        mApi = HttpClient.getInstance().createApi(NoteApi.class);
    }

    public Disposable getArticle(Consumer<List<GitHub>> consumer, ErrorAction error) {
        return mApi.getDirectory(ServiceUrls.SERVER_GITHUB + ServiceUrls.API_NOTE, Const.CLIENT_ID, Const.CLIENT_SECRET)
                .map(gitHubs -> {
                    List<GitHub> list = new ArrayList<>(gitHubs.size());
                    for (GitHub gitHub : gitHubs) {
                        if ("dir".equals(gitHub.getType()) && !"File".equals(gitHub.getPath())) {
                            list.add(gitHub);
                        }
                    }
                    return list;
                })
                .flatMap((Function<List<GitHub>, ObservableSource<List<List<GitHub>>>>)
                        gitHubs -> Observable.fromIterable(gitHubs)
                                .flatMap((Function<GitHub, ObservableSource<List<GitHub>>>)
                                        gitHub -> mApi.getArticle(gitHub.getUrl(), Const.CLIENT_ID, Const.CLIENT_SECRET))
                                .map(gitHubs1 -> {
                                    int complement = gitHubs1.size() % NoteDecoration.SPAN_COUNT;
                                    List<GitHub> list = new ArrayList<>(gitHubs1);
                                    for (int index = 0; complement > 0 && index < NoteDecoration.SPAN_COUNT - complement; index++) {
                                        list.add(new GitHub());
                                    }
                                    return list;
                                })
                                .buffer(gitHubs.size()))
                .map(lists -> {
                    List<GitHub> list = new ArrayList<>();
                    for (List<GitHub> gitHubs : lists) {
                        list.addAll(gitHubs);
                    }
                    return list;
                })
                .compose(RxSchedulers.ioSchedulers()).subscribe(consumer, error);
    }
}
