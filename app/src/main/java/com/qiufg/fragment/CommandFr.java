package com.qiufg.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qiufg.R;
import com.qiufg.fragment.base.BasePageFragment;
import com.qiufg.util.Commands;
import com.qiufg.util.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description 执行Linux指令
 * Author qiufg
 * Date 2017/2/20
 */

public class CommandFr extends BasePageFragment {

    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private Subscription subscription;

    public CommandFr() {
    }

    public static CommandFr newInstance() {
        return new CommandFr();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_command, container, false);
        ButterKnife.bind(this, view);

        ViewCompat.setNestedScrollingEnabled(mScrollView, true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @OnClick(R.id.text)
    public void exec() {
        subscription = Observable.just("cat " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/a.f")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        try {
                            return Commands.exec(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.show(getContext(), "指令读取文件失败");
        }

        @Override
        public void onNext(String s) {
            mText.setText(s);
        }
    };

    @Override
    public void fetchData() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            mSubscription = Observable.just(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/a.f")
                    .map(new Func1<String, File>() {
                        @Override
                        public File call(String s) {
                            return new File(s);
                        }
                    })
                    .map(new Func1<File, String>() {
                        @Override
                        public String call(File file) {
                            StringBuilder builder = new StringBuilder();
                            try {
                                BufferedReader br = new BufferedReader(new FileReader(file));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    builder.append(line);
                                }
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return builder.toString();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            mText.setText(s);
                        }
                    });
        }
    }
}
