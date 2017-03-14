package com.qiufg;

import android.util.SparseArray;

import com.qiufg.content.ContentApi;
import com.qiufg.content.ContentBean;
import com.qiufg.content.MyTask;
import com.qiufg.content.ResultBean;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JavaTest {

    public JavaTest() {
        sop("");
    }

    @org.junit.Test
    public void test18(){
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
        money=money.replaceAll("[零]+","零");
        sop(money);
    }

    @org.junit.Test
    public void test17() {
//        List<String> list = ["item"];
//        String item = list[0];
//
//        Set<String> set = {"item"};
//
//        Map<String, Integer> map = {"key" : 1};
//        int value = map["key"];
        String a="hello";
        String b="he"+"llo";
        sop("a==b:"+(a==b));

        String c=new String("hello");
        sop("a.equals(c):"+(a.equals(c)));
        sop("a==c:"+(a==c));
        float f=3.4f;
        StringBuffer sb=new StringBuffer();
        sb.append(new Ball(""));

        HashMap map=new HashMap();
        map.put(null,"");

    }


    @org.junit.Test
    public void test16() {
//        File file=new File("D:\\KuGou");
//        if(subscribe!=null&&subscribe.isUnsubscribed()){
//            subscribe.unsubscribe();
//        }
        Subscription subscribe = Observable.just("D:\\KuGou")
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        return new File(s);
                    }
                })
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
//                .filter(new Func1<File, Boolean>() {
//                    @Override
//                    public Boolean call(File file) {
//                        return !file.isFile();
//                    }
//                })
                .map(new Func1<File, String>() {
                    @Override
                    public String call(File file) {
                        return file.getAbsolutePath();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        sop(s);
                    }
                });
    }

    private Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onCompleted() {
            sop("");
        }

        @Override
        public void onError(Throwable e) {
            sop("");

        }

        @Override
        public void onNext(String s) {
            sop(s);
        }
    };


    @org.junit.Test
    public void test15() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ContentApi contentApi = retrofit.create(ContentApi.class);
        contentApi.getContent(10, 1)
                .map(new Func1<ResultBean, List<ContentBean>>() {
                    @Override
                    public List<ContentBean> call(ResultBean resultBean) {
                        return resultBean.getList();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Subscriber<List<ContentBean>>() {
                    @Override
                    public void onCompleted() {
                        sop("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        sop(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ContentBean> contentBeen) {
                        sop(contentBeen.size());
                    }
                });
    }

    @org.junit.Test
    public void test14() {
        MyComparator myComparator = new MyComparator();
        TreeSet<Person> set = new TreeSet<>(myComparator);
        set.add(new Person());
        set.add(new Person());
        sop(set.size());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("一", "第三个");
        hashMap.put("二", "是的");
        hashMap.put("三", "日本vty");
        hashMap.put("四", "ugfbh");
        hashMap.put("五", "还不热");
        for (Map.Entry<String, String> next : hashMap.entrySet()) {
            String key = next.getKey();
            String value = next.getValue();
            sop("key=" + key + "     value=" + value);
        }
    }

    class Person /*implements Comparable*/ {

//        @Override
//        public int compareTo(Object o) {
//            return o.hashCode();
//        }
    }

    class MyComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    }

    @org.junit.Test
    public void test13() {
        Random r = new Random();
        int count = 0;
        while (count < 2) {
            int i = r.nextInt(500 - 100 + 1) + 100;
            double v = Math.random() * 400 + 100;
            sop(i + "___" + v);
            count++;
        }

        sop("ceil:5.4: " + Math.ceil(5.4));
        sop("floor:5.6: " + Math.floor(5.6));
        sop("round:5.5: " + Math.round(5.5));
    }

    @org.junit.Test
    public synchronized void test12() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
//                    if(i==10)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sop("子线程————" + i);
                }
            }
        };
//        thread.join();
//        thread.setDaemon(true);
        thread.start();
        for (int i = 0; i < 50000; i++)
            sop("主线程————" + i);
    }


    @org.junit.Test
    public void test11() {
        MyTask task = new MyTask();

        task.setFlag(1);
        Thread t1 = new Thread(task);
        t1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyTask task2 = new MyTask();
        //改变条件
        task2.setFlag(2);
        Thread t2 = new Thread(task2);
        t2.start();
    }

    @org.junit.Test
    public void test10() {
        new Thread(new DeadLockTest(true)).start();
        new Thread(new DeadLockTest(false)).start();
    }

    private class DeadLockTest implements Runnable {
        final Object o1 = new Object();
        final Object o2 = new Object();
        private boolean flag;

        DeadLockTest(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            if (flag) {
                synchronized (o1) {
                    sop(flag + "_o1");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        sop(flag + "o2");
                    }
                }
            } else {
                synchronized (o2) {
                    sop(flag + "_o2");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        sop(flag + "o1");
                    }
                }
            }
        }
    }

    @org.junit.Test
    public void test9() {
        int[] data = {1, 2, 3, 89, 6, 74, 35, 46, 0, 8, 4};
//        Arrays.quickSort(data);
//        mySort(data);
        Arrays.sort(data);
        for (int num : data) {
            sop(num);
        }
    }

    private void mySort(int[] data) {
        if (data != null && data.length > 1) {
            _quickSort(data, 0, data.length - 1);
        }
    }

    private void _quickSort(int[] data, int left, int right) {
        if (left < right) {
            int mid = partation(data, left, right);
            _quickSort(data, 0, mid - 1);
            _quickSort(data, mid + 1, right);
        }
    }

    private int partation(int[] data, int left, int right) {
        int temp = data[left];
        while (left < right) {
            while (left < right && data[right] >= temp)
                right--;
            data[left] = data[right];
            while (left < right && data[left] <= temp)
                left++;
            data[right] = data[left];
        }
        data[left] = temp;
        return left;
    }

    @org.junit.Test
    public void test8() throws Exception {
        assertEquals(4, 2 + 2);
    }


    //    public static final String ARGS2;//static final 修饰需要立马赋值
    public final String ARGS1;

    {
        ARGS1 = "";//final修饰需要在构造函数调用前赋值
//        ARGS2="";
    }

    @org.junit.Test
    public void test7() {
//    public static void test7(){//测试方法不能用static
//        new A();
//        new JavaTest.A();
//        ARGS1 ="";

        new JavaTest();
    }

    @org.junit.Test
    public void test6() {
        double i;
        for (i = 9; i != 10; i += 0.1) {
            System.out.printf("%f\n", i);
        }
        "ss".hashCode();
    }


    @org.junit.Test
    public void test5() {
        String[] names = {"张三", "李四", "王五"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        sop("name=" + name);
                    }
                });

        Observable.from(names)
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        return Observable.just(s == null ? 0 : s.hashCode());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        sop(integer);
                    }
                });

    }

    @org.junit.Test
    public void test4() {

        int a = 10;
        int b = 10;
        Character c = 'l';
        Character d = 'l';
        sop(c == d);

        sop("Integer.MIN_VALUE=" + Integer.MIN_VALUE + "      Integer.MAX_VALUE=" + Integer.MAX_VALUE);
    }

//    static  i=0;
//    final int i;
//    public void downLoad() {
//        sop("i = " + i);
//    }


    @org.junit.Test
    public void test3() {
        sop(new JavaTest().z());
    }

    int z() {
        int x = 1;
        try {
            return 9;
        } finally {
            return 33;
        }
    }


    interface Playable {
        void play();
    }

    interface Bounceable {
        void play();
    }

    interface Rollable extends Playable, Bounceable {
//        Ball ball = new Ball("PingPang");//接口的字段默认修饰 public static final 非静态变量类型Ball不能在静态上下文中引用
    }

    class Ball implements Rollable {
        private String name;

        public String getName() {
            return name;
        }

        public Ball(String name) {
            this.name = name;
        }

        public void play() {
//            ball = new Ball("Football");//ball被final修饰，不能再赋值
//            sop(ball.getName());
        }
    }


    @org.junit.Test
    public void test2() {
        new JavaTest().test();

    }

    @org.junit.Test
    public void test1() throws Exception {

        Collection collection = new HashSet();
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(1, 2);
        collection.add(p1);
        collection.add(p2);
        collection.add(p3);
        collection.add(p1);
        p1.setX(5);
        collection.remove(p1);
        sop("collection.size()==" + collection.size());
    }


    public void test() {

        sop(super.getClass().getName());

    }

    private class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }


    private <T> void sop(T... t) {
        for (T t1 : t)
            System.out.println(t1);
        System.out.println();
    }
}