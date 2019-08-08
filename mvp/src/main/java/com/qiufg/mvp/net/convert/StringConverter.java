package com.qiufg.mvp.net.convert;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by fengguang.qiu on 2019/3/19 0019 19:40.
 * <p>
 * Desc：网络请求字符串转换器
 */
public class StringConverter implements Converter<ResponseBody, String> {

    static final StringConverter INSTANCE = new StringConverter();

    @Override
    public String convert(@NonNull ResponseBody value) throws IOException {
        return value.string();
    }
}