package com.qiufg.hotfix;


import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class BugFixManager {
    private Context mContext;

    static {
        System.loadLibrary("hotfix");
    }

    public BugFixManager(Context context) {
        mContext = context;
    }

    public void fix(File okDexFile) {
        File outFile = new File(mContext.getFilesDir(), okDexFile.getName());
        try {
            DexFile dexFile = DexFile.loadDex(okDexFile.getAbsolutePath(), outFile.getAbsolutePath(), Context.MODE_PRIVATE);
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String key = entries.nextElement();
                Class<?> okClazz = dexFile.loadClass(key, getClass().getClassLoader());
                if (okClazz == null) {
                    continue;
                }
                findBugMethod(okClazz);
            }
        } catch (IOException e) {
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
//                Method badMethod = badClazz.getDeclaredMethod(badMethodName, okMethod.getParameterTypes());
                Method badMethod = badClazz.getDeclaredMethod(badMethodName);

                replaceMethod(badMethod, okMethod);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    private void replaceMethod(Method src, Method dest) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            replaceArt(src, dest);
        } else {
            //....
        }
    }

    private native void replaceArt(Method src, Method dest);
}