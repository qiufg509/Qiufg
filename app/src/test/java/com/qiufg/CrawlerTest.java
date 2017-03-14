package com.qiufg;

/**
 * Author qiufg
 * Date 2017/2/21
 */

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerTest {

    /**
     * @网络爬虫
     */
    @Test
    public void test() {
        crawler("http://tieba.baidu.com/p/4990914629?pid=104408117227&cid=104411167651#104411167651");

    }

    //网页爬虫功能，获取指定网址的邮箱
    public void crawler(String urlPath) {
        URL ul = null;
        BufferedReader br = null;
        //建立连接
        try {
            ul = new URL(urlPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException("指定地址未找到");
        }
        URLConnection conn;
        try {
            conn = ul.openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e1) {
            throw new RuntimeException("连接失败");
        }
        //匹配指定的内容
        Pattern p = Pattern.compile("[a-zA-Z_0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+){1,2}");
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                while (m.find())
                    System.out.println(m.group());
            }
        } catch (IOException e) {
            throw new RuntimeException("读取数据失败");
        }
    }

}