package com.qiufg.net.convert;

import com.qiufg.util.FileUtils;
import com.qiufg.util.LogUtils;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description
 * Author qiufg
 * Date 2017/3/15 1:37
 */

public class WriteFileManager {

    public static boolean writeResponseBodyToDisk(ResponseBody body, String downloadName) {
        String path = FileUtils.getVideoPath() + downloadName;
        LogUtils.i("WriteFileManager.startToWrite.path:" + path);

        File futureFile = new File(path);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        long fileSize = 0;
        try {
            fileSize = body.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("WriteFileManager.writeResponseBodyToDisk.fileSize:" + fileSize);

        try {
            byte[] fileReader = new byte[1024 * 1024];
            long fileSizeDownloaded = 0;
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(futureFile);

            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
            }
            LogUtils.d("file download: " + fileSizeDownloaded + " of " + fileSize);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}