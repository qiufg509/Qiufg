package com.qiufg.mvp.net.api;

import com.qiufg.mvp.net.ServiceUrls;
import com.qiufg.mvp.net.respond.ResultArray;
import com.qiufg.mvp.net.respond.ResultObject;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by fengguang.qiu on 2019/08/07 10:46.
 * <p>
 * Desc：服务器请求接口
 * <p>
 * 1. 请求方式 @GET、@POST
 * 2. 添加请求头 @Headers、@Header、@HeaderMap
 * 3. 替换接口中路径 @Path
 * 4. 动态设置请求接口地址 @Url
 * 5. 以键值对查询 @Query、@QueryMap
 * 6. 以表单形式提交请求 @FormUrlEncoded、@Field、@FieldMap
 * 7. 以表单形式上传文件 @Multipart、@Part、@PartMap
 * 8. 响应以流形式返回 @Streaming（用于下载文件）
 * 9. @FormUrlEncoded、@Multipart、@Body三者互斥，任意两个不能同时使用
 * 10. @Path、@Url不能同时使用
 */
public interface ServiceApi {

    /**
     * http://gank.io/api/data/福利/1/2
     *
     * @param number number
     * @param page   page
     * @return result
     */
    @GET(ServiceUrls.TEST_TEST_GANK)
    Flowable<String> apiGet1(@Path("number") int number, @Path("page") int page);

    /**
     * http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=关键字&bk_length=600
     * <p>
     * 百科查询测试接口 (接口不稳定多试几次)
     */
    int t_scope = 103;
    String t_format = "json";
    String t_appid = "379020";
    int t_bk_length = 600;

    @GET(ServiceUrls.TEST_TEST_BAIKE)
    Flowable<ResultObject> apiGet2(@Query("scope") int scope,
                                   @Query("format") String format,
                                   @Query("appid") String appid,
                                   @Query("bk_key") String bk_key,
                                   @Query("bk_length") int bk_length);

    /**
     * http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=关键字&bk_length=600
     *
     * @param scope     scope
     * @param format    format
     * @param appid     appid
     * @param bk_key    bk_key
     * @param bk_length bk_length
     * @return bean
     */
    @FormUrlEncoded
    @POST(ServiceUrls.TEST_TEST_BAIKE)
    Flowable<ResultArray> apiPost(@Field("scope") int scope,
                                  @Field("format") String format,
                                  @Field("appid") String appid,
                                  @Field("bk_key") String bk_key,
                                  @Field("bk_length") int bk_length);

    /**
     * 上传文件
     *
     * @param file RequestBody body = RequestBody.create(MediaType.parse("text/*"), file);
     * @return 结果String
     * <p>
     * 表单上传
     * RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
     * MultipartBody.Part image = MultipartBody.Part.createFormData("Avatar", file.getName(), requestFile);
     * RequestBody body = image.body();
     */
    @Multipart
    @POST(ServiceUrls.API_TEST_UPLOAD)
    Flowable<ResultObject> uploadFile(@Header("Authorization") String authorization,
                                      @Header("timestamp") String timestamp,
                                      @Header("sign") String sign,
                                      @Part("UploadForm[avatar]") RequestBody file);

    /**
     * 下载
     *
     * @param url 下载链接
     * @return InputStream is = body.byteStream();
     */
    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String url);
}
