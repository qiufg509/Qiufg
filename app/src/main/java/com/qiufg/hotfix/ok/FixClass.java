package com.qiufg.hotfix.ok;

import com.qiufg.hotfix.MethodReplace;

/**
 * Description
 * Author qiufg
 * Date 2017/3/15 4:05
 */

public class FixClass {

    @MethodReplace(clazzName = "com.qiufg.activity.HotfixAct", methodName = "divide")
    public int divide() {
        int args1 = 12;
        int args2 = 2;
        return args1 / args2;
    }
}
