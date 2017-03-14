package com.qiufg;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

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
