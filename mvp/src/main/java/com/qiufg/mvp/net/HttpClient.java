package com.qiufg.mvp.net;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qiufg.mvp.R;
import com.qiufg.mvp.base.App;
import com.qiufg.mvp.db.DBManager;
import com.qiufg.mvp.net.api.ServiceApi;
import com.qiufg.mvp.util.Logger;
import com.qiufg.mvp.util.SPUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fengguang.qiu on 2019/3/19 19:40.
 * <p>
 * Desc：网络请求controller
 * <p>
 * 网络请求步骤 见{@link ServiceApi}
 * <p>
 * 一个服务器地址对应一个api
 * 调用不同服务器接口需要在此对应api获取方法
 * <p>
 * path 是绝对路径的形式：
 * path = "/apath", baseUrl = "http://host:port/a/b"
 * Url = "http://host:port/apath"
 * <p>
 * path 是相对路径，baseUrl 是目录形式：(推荐)
 * path = "apath", baseUrl = "http://host:port/a/b/"
 * Url = "http://host:port/a/b/apath"
 * <p>
 * path 是相对路径，baseUrl 是文件形式：
 * path = "apath", baseUrl = "http://host:port/a/b"
 * Url = "http://host:port/a/apath"
 * <p>
 * path 是完整的 Url：
 * path = "http://host:port/aa/apath", baseUrl = "http://host:port/a/b"
 * Url = "http://host:port/aa/apath"
 */
abstract class HttpClient {

    private static final String TAG = "HttpClient";
    private ServiceApi mForestServiceApi;
    public static String SERVER_URL = ServiceUrls.SERVER_URL;

    HttpClient() {
    }

    private final OkHttpClient.Builder mBuilder = App.getInstance().isDebug()
            ? new OkHttpClient.Builder()
            .addInterceptor(new HttpLogInterceptor(HttpClient.class.getSimpleName()))
            : new OkHttpClient.Builder();
    private final OkHttpClient client = mBuilder
            .sslSocketFactory(getSSLSocketFactory())
            .hostnameVerifier(new UnSafeHostnameVerifier())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)
            .build();

    /**
     * 获取网络请求接口
     *
     * @return 上传数据结果 {@link ServiceApi}
     */
    ServiceApi getServiceApi() {
        if (mForestServiceApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mForestServiceApi = retrofit.create(ServiceApi.class);
        }
        return mForestServiceApi;
    }

    public void resetBaseUrl(String serverUrl) {
        if (TextUtils.isEmpty(serverUrl) || !serverUrl.startsWith("http")) return;
        SERVER_URL = serverUrl;
        Logger.e(TAG, "doNext: 退出 清理数据");
        DBManager.getInstance().clearAll();
        SPUtils.clearUser();
        mForestServiceApi = null;
    }

    private SSLSocketFactory getSSLSocketFactory() {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(App.getInstance().getResources().openRawResource(R.raw.https));
            if (certificates.isEmpty()) {
                throw new IllegalArgumentException("expected non-empty set of trusted certificates");
            }

            // Put the certificates a key store.
            String clientKetPassword = getPassword();
            char[] password = clientKetPassword.toCharArray(); // Any password will work.
            KeyStore keyStore = newEmptyKeyStore(password);
            int index = 0;
            for (Certificate certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificate);
            }

            //  keyStore.load(in,clientKetPassword.toCharArray());
            // Use it to build an X509 trust manager.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }

            SSLContext ssContext = SSLContext.getInstance("SSL");
            ssContext.init(keyManagerFactory.getKeyManagers(), trustManagers, null);
            //return (X509TrustManager) trustManagers[0];
            return ssContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }


    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private String getPassword() {
        String result;
        try {
            BufferedSource buffer = Okio.buffer(Okio.source(App.getInstance().getResources().openRawResource(R.raw.https_key)));
            result = buffer.readByteString().utf8();
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    private class UnSafeHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;//自行添加判断逻辑，true->Safe，false->unsafe
        }
    }
}
