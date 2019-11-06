package com.qiufg.template.andfix;


import android.content.Context;

import com.qiufg.template.App;
import com.qiufg.template.util.CommonUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class BugFixManager {

    static {
        System.loadLibrary("andfix");
    }

    private static BugFixManager mFixManager;

    private BugFixManager() {
    }

    public static BugFixManager getInstance() {
        if (mFixManager == null) {
            synchronized (BugFixManager.class) {
                if (mFixManager == null) {
                    mFixManager = new BugFixManager();
                }
            }
        }
        return mFixManager;
    }

    public void fix(String sourcePath) {
        try {
            File okDexFile = new File(sourcePath);
            if (okDexFile.exists() || okDexFile.isDirectory()) {
                return;
            }
            String versionStr = sourcePath.substring(sourcePath.lastIndexOf("_") + 1, sourcePath.lastIndexOf("."));
            if (Long.valueOf(versionStr) != CommonUtils.getVersionCode()) {
                return;
            }
            DexFile dexFile = DexFile.loadDex(okDexFile.getAbsolutePath(), App.getInstance().getFilesDir().getAbsolutePath(), Context.MODE_PRIVATE);
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String key = entries.nextElement();
                Class<?> okClazz = dexFile.loadClass(key, getClass().getClassLoader());
                if (okClazz == null) {
                    continue;
                }
                findBugMethod(okClazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findBugMethod(Class<?> okClazz) {
        Method[] methods = okClazz.getDeclaredMethods();
        for (Method okMethod : methods) {
            MethodReplace methodReplace = okMethod.getAnnotation(MethodReplace.class);
            if (methodReplace == null) {
                continue;
            }
            String badClazzName = methodReplace.clazzName();
            String badMethodName = methodReplace.methodName();

            try {
                Class<?> badClazz = Class.forName(badClazzName);
                Method badMethod = badClazz.getDeclaredMethod(badMethodName, okMethod.getParameterTypes());
//                Method badMethod = badClazz.getDeclaredMethod(badMethodName);

                replaceMethod(badMethod, okMethod);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    private void replaceMethod(Method src, Method dest) {
        replaceArt(src, dest);
    }

    private native void replaceArt(Method src, Method dest);
}