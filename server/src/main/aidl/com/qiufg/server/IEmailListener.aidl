// IEmailListener.aidl
package com.qiufg.server;

import com.qiufg.template.bean.EmailBean;
// Declare any non-default types here with import statements

interface IEmailListener {

    void onEmailReceived(in EmailBean email);
}
