package com.qiufg.template.net.convert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by fengguang.qiu on 2019/3/19 0019 19:40.
 * <p>
 * Desc：网络请求字符串转换Factory
 */
public class StringConverterFactory extends Converter.Factory {

    private static final StringConverterFactory INSTANCE = new StringConverterFactory();

    public static StringConverterFactory create() {
        return INSTANCE;
    }

    // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return StringConverter.INSTANCE;
        }
        //其它类型我们不处理，返回null就行
        return null;
    }
}
