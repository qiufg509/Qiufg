// ISecurityCenter.aidl
package com.qiufg.server;

// Declare any non-default types here with import statements

interface ISecurityCenter {

    String encrypt (String content);

    String decrypt (String encryptText);
}
