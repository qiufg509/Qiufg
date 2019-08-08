package com.qiufg;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import com.qiufg.util.Money;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AndroidTest {


    @Test
    public void test3(){
        String input = "463606400";
        SparseArray<String> num = new SparseArray<>(10);
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
        String[] unit = {"元", "拾", "佰", "仟", "万", "亿"};
        StringBuilder sb = new StringBuilder();
        char[] chars = input.toCharArray();
        for (int i = chars.length - 1, count = 0; i >= 0; i--, count++) {
            switch (count) {
                case 0:
                    sb.append(unit[0]);
                    break;
                case 1:
                    sb.append(unit[1]);
                    break;
                case 2:
                    sb.append(unit[2]);
                    break;
                case 3:
                    sb.append(unit[3]);
                    break;
            }
            sb.append(num.get(i));
        }
        String money = sb.reverse().toString();
        money = money.replaceAll("[零]+", "零");
        sop(money);
    }

    @Test
    public void test2() {

        String input = "6000450030";
        try {
            String s = Money.switchMoney(input);
            sop(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void test1() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        sop("widthPixels=" + widthPixels + "        heightPixels=" + heightPixels);

//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        int screenWidth = screenWidth = display.getWidth();
//        int screenHeight = screenHeight = display.getHeight();
//
//        // 方法2
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        TextView tv = (TextView)this.findViewById(R.id.tv);
//        float width=dm.widthPixels*dm.density;
//        float height=dm.heightPixels*dm.density;
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    private void sop() {
        System.out.println();
    }

    private <T> void sop(T t) {
        System.out.println(t);
    }
}
