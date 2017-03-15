package com.qiufg.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.qiufg.R;
import com.qiufg.net.NetWork;
import com.qiufg.net.convert.WriteFileManager;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DownloadAct extends AppCompatActivity {

    @BindView(R.id.download1)
    TextView mDownload1;
    @BindView(R.id.download2)
    TextView mDownload2;
    @BindView(R.id.download3)
    TextView mDownload3;
    @BindView(R.id.download4)
    TextView mDownload4;
    private Subscription mSubscription;
    private String fileUrl_1 = "31E8015A9F80B50A8168A91150848466.mp4?sc=6e3b688a74d0020d";
    private String fileUrl_2 = "D1E9015A4C00A76EDBBFDDD9F6E70995.mp4?sc=fa771a79e5c66d74";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_download);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @OnClick(R.id.download1)
    public void download1() {
        mSubscription = NetWork.getDownLoadApi()
                .downloadFile(fileUrl_1)
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody responseBody) {

                        return WriteFileManager.writeResponseBodyToDisk(responseBody, fileUrl_1.substring(fileUrl_1.lastIndexOf("/") + 1));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    Observer<Boolean> mObserver = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Boolean flag) {
            if (flag)
                mDownload1.setText("下载完成");
        }
    };


    @OnClick(R.id.download2)
    public void download2() {
        ArrayList<String> mDownloadList = new ArrayList<>();
        mDownloadList.add(fileUrl_1);
        mDownloadList.add(fileUrl_2);

        List<Observable<Boolean>> observables = new ArrayList<>();
        //将所有的Observable放到List中
        for (int i = 0; i < mDownloadList.size(); i++) {
            final String downloadUrl = mDownloadList.get(i);
            observables.add(
                    NetWork.getDownLoadApi()
                            .downloadFile(mDownloadList.get(i))
                            .subscribeOn(Schedulers.io())
                            .map(new Func1<ResponseBody, Boolean>() {
                                @Override
                                public Boolean call(ResponseBody responseBody) {
                                    return WriteFileManager.writeResponseBodyToDisk(responseBody, downloadUrl.hashCode() + ".mp4");
                                }
                            })
                            .subscribeOn(Schedulers.io()));
        }

        //Observable的merge将所有的Observable合成一个Observable,所有的observable同时发射数据。
        Observable.merge(observables).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean flag) {
                        if (flag) {
                            mDownload2.setText("下载完成");
                        }
                    }
                });
    }
}
