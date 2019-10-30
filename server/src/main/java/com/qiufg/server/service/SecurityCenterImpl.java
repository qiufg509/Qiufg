package com.qiufg.server.service;

import com.qiufg.server.ISecurityCenter;
import com.qiufg.server.util.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by qiufg on 2019/10/30 14:11.
 * <p>
 * Desc：Binder加密解密
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final String PASSWORD = "github";

    @Override
    public String encrypt(String content) {
        try {
            return AESCrypt.encrypt(PASSWORD, content);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String encryptText) {
        try {
            return AESCrypt.decrypt(PASSWORD, encryptText);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
