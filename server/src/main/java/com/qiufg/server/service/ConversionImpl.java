package com.qiufg.server.service;

import com.qiufg.server.IConversion;
import com.qiufg.server.util.Money;

/**
 * Created by qiufg on 2019/10/30 14:14.
 * <p>
 * Desc：Binder金额转换
 */
public class ConversionImpl extends IConversion.Stub {

    @Override
    public String switchMoney(double money) {
        return Money.switchMoney(money);
    }
}
