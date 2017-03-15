package com.qiufg.util;

import android.os.Environment;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author qiufg
 * Date 2017/3/15 1:45
 */
public class FileUtils {

    /**
     * 获得video的路径
     *
     * @return
     */
    public static String getVideoPath() {
        String videoPath = Environment.getExternalStorageDirectory() + "/Download/";
        return videoPath;
    }

    /**
     * 读取获得video的路径下所有的文件名
     *
     * @return fileNameList
     */
    public static List<String> getAllFileName() {
        List<String> fileNameList = new ArrayList<>();
        File file = new File(getVideoPath());
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File subFile : files) {

                try {
                    fileNameList.add(new String(subFile.getName().getBytes(), "utf-8"));
                    LogUtils.i("FileUtil.getAllFileName." + new String(subFile.getName().getBytes(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        } else {
            LogUtils.e("file is not exist");
        }
        return fileNameList;
    }

    /**
     * 删除文件
     *
     * @param path path
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * 是否有足够大小的空间存储下载的文件
     *
     * @param file 下载文件
     * @return
     */
    public static boolean isEnoughSpace(File file) {
        //判断剩余的内存大小,存储大容量的文件时使用
        LogUtils.i("FileUtil.isEnoughSpace.file.length():" + file.length());
        LogUtils.i("FileUtil.isEnoughSpace.usableSpace:" + Environment.getDataDirectory().getUsableSpace());
        return Environment.getDataDirectory().getUsableSpace() > file.length();
    }


}