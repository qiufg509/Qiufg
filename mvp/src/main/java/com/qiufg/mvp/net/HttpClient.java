package com.qiufg.mvp.net;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qiufg.mvp.App;
import com.qiufg.mvp.R;
import com.qiufg.mvp.db.DBManager;
import com.qiufg.mvp.util.Logger;
import com.qiufg.mvp.util.SPUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
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
 * 网络请求步骤 见{@link com.qiufg.mvp.module.main.model.GirlApi}
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
public class HttpClient {

    private static final String TAG = "HttpClient";
    private static String SERVER_URL = ServiceUrls.SERVER_URL;
    private static HttpClient sHttpClient = null;
    private static Retrofit sRetrofit = null;

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (sHttpClient == null) {
            synchronized (HttpClient.class) {
                if (sHttpClient == null) {
                    sHttpClient = new HttpClient();
                }
            }
        }
        return sHttpClient;
    }

    public <T> T createApi(Class<T> cls) {
        return getRetrofit().create(cls);
    }

    /**
     * 获取网络请求接口
     *
     * @return 上传数据结果 {@link com.qiufg.mvp.module.main.model.GirlApi}
     */
    private Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (HttpClient.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .client(newClient())
                            .baseUrl(SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    /**
     * 创建新的OkHttpClient
     *
     * @return OkHttpClient
     */
    private OkHttpClient newClient() {
        OkHttpClient.Builder builder = App.getInstance().isDebug()
                ? new OkHttpClient.Builder()
                .addInterceptor(new HttpLogInterceptor(HttpClient.class.getSimpleName()))
                : new OkHttpClient.Builder();
//        trustSafeHost(builder);
//        trustAll(builder);
        return builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .proxy(Proxy.NO_PROXY)
                .build();
    }

    /**
     * 信任证书认证
     *
     * @param builder OkHttpClient.Builder
     */
    private void trustSafeHost(OkHttpClient.Builder builder) {
        char[] password = getPassword();
        KeyStore keyStore = getKeyStore(password);
        X509TrustManager trustManager = getTrustManager(keyStore);
        SSLSocketFactory sslSocketFactory = getSSLSocketFactory(password, keyStore, trustManager);
//        SSLSocketFactory sslSocketFactory1 = _getSSLSocketFactory();
        if (sslSocketFactory != null && trustManager != null) {
            builder.sslSocketFactory(sslSocketFactory, trustManager)
                    .hostnameVerifier(new UnSafeHostnameVerifier());
        }
    }

    /**
     * 全部信任
     *
     * @param builder OkHttpClient.Builder
     */
    private void trustAll(OkHttpClient.Builder builder) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置请求域名
     *
     * @param serverUrl 新地址
     */
    public void resetBaseUrl(String serverUrl) {
        if (TextUtils.isEmpty(serverUrl) || !serverUrl.startsWith("http")) return;
        SERVER_URL = serverUrl;
        Logger.e(TAG, "doNext: 退出 清理数据");
        DBManager.getInstance().clearAll();
        SPUtils.clearUser();
        sRetrofit = null;
    }

    /**
     * pem格式证书
     *
     * @return SSLSocketFactory
     */
    private SSLSocketFactory getSSLSocketFactory(char[] password, KeyStore keyStore, X509TrustManager trustManager) {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);
            SSLContext ssContext = SSLContext.getInstance("SSL");
            ssContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[]{trustManager}, null);
            return ssContext.getSocketFactory();
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private char[] getPassword() {
        try {
            BufferedSource buffer = Okio.buffer(Okio.source(App.getInstance().getResources().openRawResource(R.raw.https_key)));
            String result = buffer.readByteString().utf8();
            buffer.close();
            return result.toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyStore getKeyStore(char[] password) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(
                    App.getInstance().getResources().openRawResource(R.raw.https));
            if (certificates.isEmpty()) {
                throw new IllegalArgumentException("expected non-empty set of trusted certificates");
            }
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, password);
            int index = 0;
            for (Certificate certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificate);
            }
            return keyStore;
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X509TrustManager getTrustManager(KeyStore keyStore) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class UnSafeHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;//自行添加判断逻辑，true->Safe，false->unsafe
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * crt格式证书
     *
     * @return SSLSocketFactory
     */
    private SSLSocketFactory _getSSLSocketFactory() {
        InputStream caInput = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // uwca.crt 打包在 asset 中，该证书可以从https://itconnect.uw.edu/security/securing-computer/install/safari-os-x/下载
            caInput = new BufferedInputStream(App.getInstance().getAssets().open("https.crt"));
            Certificate ca = cf.generateCertificate(caInput);
            Logger.i("Longer", "ca=" + ((X509Certificate) ca).getSubjectDN());
            Logger.i("Longer", "key=" + ca.getPublicKey());
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLSv1", "AndroidOpenSSL");
            context.init(null, tmf.getTrustManagers(), null);
            return context.getSocketFactory();
        } catch (CertificateException | IOException | NoSuchAlgorithmException
                | KeyStoreException | KeyManagementException | NoSuchProviderException e) {
            e.printStackTrace();
        } finally {
            if (caInput != null) {
                try {
                    caInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
