package com.qiufg.server.util;

import android.util.SparseArray;

import androidx.annotation.NonNull;

/**
 * Description
 * Author qiufg
 * Date 2017/3/13 7:28
 */

public class Money {
    private static SparseArray<String> num = new SparseArray<>(10);

    static {
        num.append(0, "零");
        num.append(1, "壹");
        num.append(2, "贰");
        num.append(3, "叁");
        num.append(4, "肆");
        num.append(5, "伍");
        num.append(6, "陆");
        num.append(7, "柒");
        num.append(8, "捌");
        num.append(9, "玖");
    }

    public static String switchMoney(double money) {
        return switchMoney(String.valueOf(money));
    }

    private static String switchMoney(@NonNull String money) {
        String[] split = money.replaceAll(" ", "").split("\\.");
        StringBuilder builder = new StringBuilder();
        if (split[0].length() > 0 && split[0].length() <= 13) {
            builder.append(yuan(split[0]));
        } else {
            return "只支持9999999999999以内金额";
        }
        if (split.length == 2 && split[1].length() > 0 && split[1].length() <= 3) {
            builder.append(jiaofen(split[1]));
        } else if (split.length == 2 && split[1].length() > 3) {
            return "只能精确到厘";
        } else if (split.length > 2) {
            return "金额格式错误";
        }
        return builder.toString();
    }

    private static String yuan(@NonNull String money) throws NumberFormatException {
        String[] unit = {"元元", "拾", "佰", "仟", "万万", "拾", "佰", "仟", "亿亿", "拾", "佰", "仟", "万"};
        StringBuilder sb = new StringBuilder();
        char[] chars = money.toCharArray();
        for (int i = chars.length - 1, j = 0; i >= 0; i--, j++) {
            sb.append(unit[j]).append(num.get(Integer.valueOf(String.valueOf(chars[i]))));
        }
        String yuan = sb.reverse().toString();
        yuan = yuan.replaceAll("零[拾佰仟]", "零")
                .replaceAll("(零)\\1+", "$1")
                .replaceAll("零[元万亿]", "")
                .replace("亿万元", "亿元")
                .replaceAll("(.)\\1+", "$1");
        if (yuan.startsWith("壹拾")) {
            yuan = yuan.substring(1);
        }
        return yuan;
    }

    private static String jiaofen(@NonNull String jiaofen) throws NumberFormatException {
        char[] chars = jiaofen.toCharArray();
        StringBuilder builder = new StringBuilder();
        int jiao = Integer.valueOf(String.valueOf(chars[0]));
        if (jiao > 0 && jiao < 10) {
            builder.append(num.get(jiao)).append("角");
        }
        if (chars.length >= 2) {
            int fen = Integer.valueOf(String.valueOf(chars[1]));
            if (fen > 0 && fen < 10) {
                builder.append(num.get(fen)).append("分");
            }
            if (chars.length == 3) {
                int li = Integer.valueOf(String.valueOf(chars[2]));
                if (li > 0 && li < 10) {
                    builder.append(num.get(li)).append("厘");
                }
            }
        }
        return builder.toString();
    }
}
