package com.qiufg.net.convert;

import com.qiufg.model.GirlBean;
import com.qiufg.model.GirlResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.functions.Func1;

/**
 * Author qiufg
 * Date 2017/2/20
 */

public class GirlParser implements Func1<GirlResult, List<GirlBean>> {


    @Override
    public List<GirlBean> call(GirlResult girlResult) {
        //可做本地数据处理

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.CHINA);
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        List<GirlBean> list = new ArrayList<>(girlResult.getList().size());
        for (GirlBean girlBean : girlResult.getList()) {
            String date = null;
            try {
                date = output.format(input.parse(girlBean.getCreatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            girlBean.setCreatedAt(date);
            list.add(girlBean);
        }
        return list;
    }
}
