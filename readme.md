一、Desc：服务器请求接口
1. 请求方式 @GET、@POST
2. 添加请求头 @Headers、@Header、@HeaderMap
3. 替换接口中路径 @Path
4. 动态设置请求接口地址 @Url
5. 以键值对查询 @Query、@QueryMap
6. 以表单形式提交请求 @FormUrlEncoded、@Field、@FieldMap
7. 以表单形式上传文件 @Multipart、@Part、@PartMap
8. 响应以流形式返回 @Streaming（用于下载文件）
9. @FormUrlEncoded、@Multipart、@Body三者互斥，任意两个不能同时使用
10. @Path、@Url不能同时使用

二、换肤
参考 git@github.com:qiufg509/Android-skin-support.git