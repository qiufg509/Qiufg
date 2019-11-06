package com.qiufg.template.andfix;

/**
 * Description
 * Author qiufg
 * Date 2017/3/15 4:05
 */

public class FixClass {

    @MethodReplace(clazzName = "com.qiufg.template.common.activity.AndFixActivity", methodName = "destination")
    public int destination() {
        int args1 = 12;
        int args2 = 2;
        return args1 / args2;
    }
}
