package com.qiufg.net.convert;

import com.qiufg.model.AndroidBean;
import com.qiufg.model.AndroidResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Author qiufg
 * Date 2017/3/6
 */
public class AndroidParser implements rx.functions.Func1<com.qiufg.model.AndroidResult, List<AndroidBean>> {
    @Override
    public List<AndroidBean> call(AndroidResult androidResult) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SS'Z'", Locale.CHINA);
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.CHINA);
        List<AndroidBean> androidBeens = new ArrayList<>(androidResult.getList().size());
        for (AndroidBean androidBean : androidResult.getList()) {
            String format = null;
            try {
                format = output.format(input.parse(androidBean.getCreatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            androidBean.setCreatedAt(format);
            androidBeens.add(androidBean);
        }
        return androidBeens;
    }
}
