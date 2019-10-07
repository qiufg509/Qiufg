package com.qiufg.mvp.module.preview.presenter;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.qiufg.mvp.App;
import com.qiufg.mvp.bus.RxSchedulers;
import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.exception.QiufgException;
import com.qiufg.mvp.module.base.BasePresenter;
import com.qiufg.mvp.module.preview.view.PreviewView;
import com.qiufg.mvp.util.FileUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengguang.qiu on 2019/09/29 16:30.
 * <p>
 * Desc：照片预览Presenter
 */
public class PreviewPresenter extends BasePresenter<PreviewView> {

    private static final int TYPE_DOWNLOAD = 0;
    private static final int TYPE_WALLPAPER = 1;
    private static final int TYPE_LOCK_WRAPPER = 2;

    private void download(String url, int type) {
        Glide.with(App.getInstance()).asFile().load(url).into(new CustomTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                switch (type) {
                    case TYPE_DOWNLOAD:
                        downloadPhoto(resource);
                        break;
                    case TYPE_WALLPAPER:
                        setWallPaper(resource);
                        break;
                    case TYPE_LOCK_WRAPPER:
                        setLockWrapper(resource);
                        break;
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    public void downloadPhoto(String url) {
        download(url, TYPE_DOWNLOAD);
    }

    private void downloadPhoto(File image) {
        Disposable disposable = Flowable.just(image)
                .map(FileUtils::saveFile2Album)
                .compose(RxSchedulers.ioSchedulers())
                .subscribe(new DownloadSubscriber(mView), new ErrorAction() {
                    @Override
                    public void doNext(QiufgException e) {
                        mView.downloadPhotoFail();
                    }
                });
        mDisposable.add(disposable);
    }

    private static class DownloadSubscriber implements Consumer<String> {
        private WeakReference<PreviewView> mReference;

        DownloadSubscriber(PreviewView previewView) {
            mReference = new WeakReference<>(previewView);
        }

        @Override
        public void accept(String data) {
            if (mReference == null || mReference.get() == null) return;
            mReference.get().downloadPhotoSuccess(data);
        }
    }

    public void setWallPaper(String url) {
        download(url, TYPE_WALLPAPER);
    }

    private void setWallPaper(File image) {
        Disposable disposable = Flowable.just(image)
                .doOnNext(file -> {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(App.getInstance().getContentResolver(), Uri.fromFile(file));
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(App.getInstance());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    } else {
                        wallpaperManager.setBitmap(bitmap);
                    }
                    bitmap.recycle();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WallPaperSubscriber(mView), new ErrorAction() {
                    @Override
                    public void doNext(QiufgException e) {
                        mView.setWallpaperFail();
                    }
                });
        mDisposable.add(disposable);
    }

    private static class WallPaperSubscriber implements Consumer<File> {
        private WeakReference<PreviewView> mReference;

        WallPaperSubscriber(PreviewView previewView) {
            mReference = new WeakReference<>(previewView);
        }

        @Override
        public void accept(File data) {
            if (mReference == null || mReference.get() == null) return;
            mReference.get().setWallpaperSuccess();
        }
    }

    public void setLockWrapper(String url) {
        download(url, TYPE_LOCK_WRAPPER);
    }

    private void setLockWrapper(File image) {
        Disposable disposable = Flowable.just(image)
                .doOnNext(file -> {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(App.getInstance().getContentResolver(), Uri.fromFile(file));
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(App.getInstance());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                    } else {
                        Class aClass = wallpaperManager.getClass();
                        Method setWallPaperMethod = aClass.getMethod("setBitmapToLockWallpaper", Bitmap.class);
                        setWallPaperMethod.invoke(wallpaperManager, bitmap);
                    }
                    bitmap.recycle();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LockWrapperSubscriber(mView), new ErrorAction() {
                    @Override
                    public void doNext(QiufgException e) {
                        mView.setLockWrapperFail();
                    }
                });
        mDisposable.add(disposable);
    }

    private static class LockWrapperSubscriber implements Consumer<File> {
        private WeakReference<PreviewView> mReference;

        LockWrapperSubscriber(PreviewView previewView) {
            mReference = new WeakReference<>(previewView);
        }

        @Override
        public void accept(File data) {
            if (mReference == null || mReference.get() == null) return;
            mReference.get().setLockWrapperSuccess();
        }
    }
}
