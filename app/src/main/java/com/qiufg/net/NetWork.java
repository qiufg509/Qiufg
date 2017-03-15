package com.qiufg.net;

import com.qiufg.net.api.AndroidListApi;
import com.qiufg.net.api.DownLoadApi;
import com.qiufg.net.api.GirlListApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.CallAdapter;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Author qiufg
 * Date 2017/2/20
 */

public class NetWork {
    private static GirlListApi mGirlListApi;
    private static AndroidListApi androidListApi;
    private static DownLoadApi mDownLoadApi;

    private static final String BASE_URL = "http://gank.io/api/";
    private static final String BASE_URL2 = "http://hc.yinyuetai.com/uploads/videos/common/";
    private static Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory mRxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static DownLoadApi getDownLoadApi() {
        if (mDownLoadApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient())
                    .baseUrl(BASE_URL2)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .build();
            mDownLoadApi = retrofit.create(DownLoadApi.class);
        }
        return mDownLoadApi;
    }

    public static GirlListApi getGirlListApi() {
        if (mGirlListApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .build();
            mGirlListApi = retrofit.create(GirlListApi.class);
        }
        return mGirlListApi;
    }

    public static AndroidListApi getAndroidListApi() {
        if (androidListApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .build();
            androidListApi = retrofit.create(AndroidListApi.class);
        }
        return androidListApi;
    }
}
