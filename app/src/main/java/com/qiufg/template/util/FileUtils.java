package com.qiufg.template.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.qiufg.template.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import okhttp3.ResponseBody;

/**
 * Created by fengguang.qiu on 2019/04/06 17:41.
 * <p>
 * Desc：文件相关操作工具
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * 保存数据到文件
     *
     * @param content  要保存的内容
     * @param savePath 保存的路径
     * @param fileName 保存的文件名
     */
    public static void output2File(@NonNull String content, String savePath, String fileName) {
        FileOutputStream fos = null;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File dir = new File(savePath);
                if (!dir.exists()) {
                    boolean mkdirs = dir.mkdirs();
                    Logger.i("目录创建" + mkdirs);
                }
                File file = new File(savePath, fileName);
                if (!file.exists()) {
                    boolean newFile = file.createNewFile();
                    Logger.i("日志文件创建" + newFile);
                }
                fos = new FileOutputStream(file, true);
                byte[] errorInfo = content.getBytes(StandardCharsets.UTF_8);
//                if (BuildConfig.DEBUG) {
//                    for (int i = 0; i < errorInfo.length; i++) {
//                        errorInfo[i] ^= 'Q' ^ 'F' ^ 'G';
//                    }
//                }
                fos.write(errorInfo);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存文件
     *
     * @param body         响应体
     * @param dir          存放文件夹
     * @param downloadName 文件名
     * @return 文件路径
     */
    public static String writeResponseBodyToDisk(ResponseBody body, File dir, String downloadName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File futureFile = new File(dir, downloadName);
        try {
            if (!dir.exists()) {
                boolean mkdirs = dir.mkdirs();
                Logger.d(TAG, "onSuccess: create dir " + mkdirs);
            }
            if (!futureFile.exists()) {
                boolean newFile = futureFile.createNewFile();
                Logger.d(TAG, "onSuccess: newFile = " + newFile);
            }
            Logger.i("WriteFileManager.startToWrite.path:" + futureFile.getAbsolutePath());

            long fileSize = body.contentLength();
            Logger.d("writeResponseBodyToDisk.fileSize:" + fileSize);
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
            Logger.d("file download: " + fileSizeDownloaded + " of " + fileSize);
            outputStream.flush();
            return futureFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            if (futureFile.exists()) {
                boolean delete = futureFile.delete();
                Logger.i("下载失败删除文件 " + delete + "\t" + e.getMessage());
            }
            return null;
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

    /**
     * 保存文件到相册
     *
     * @param source 源文件
     * @return 保存路径
     */
    public static String saveFile2Album(File source) {
        FileInputStream is = null;
        FileOutputStream os = null;
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera";
        try {
            int hashCode = source.getName().hashCode();
            String fileName = ((hashCode < 0) ? -hashCode : hashCode) + ".jpg";
            File dir = new File(galleryPath);
            if (dir.exists()) {
                if (!dir.isDirectory()) {
                    boolean delete = dir.delete();
                    boolean mkdirs = dir.mkdirs();
                    Logger.v("delete = " + delete + "\tmkdirs = " + mkdirs);
                }
            } else {
                boolean mkdirs = dir.mkdirs();
                Logger.v("mkdirs = " + mkdirs);
            }
            File target = new File(galleryPath, fileName);
            if (!target.exists()) {
                boolean newFile = target.createNewFile();
                Logger.v("newFile = " + newFile);
            }
            is = new FileInputStream(source);
            os = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }
            os.flush();

            //通知相册更新
            MediaStore.Images.Media.insertImage(App.getInstance().getContentResolver(),
                    target.getAbsolutePath(), target.getName(), null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(target);
            intent.setData(uri);
            App.getInstance().sendBroadcast(intent);

            return galleryPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
