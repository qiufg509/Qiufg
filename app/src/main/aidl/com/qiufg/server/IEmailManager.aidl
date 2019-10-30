// IEmailManager.aidl
package com.qiufg.server;

import com.qiufg.server.IEmailListener;
import com.qiufg.template.bean.EmailBean;
// Declare any non-default types here with import statements

interface IEmailManager {

    List<EmailBean> getAllEmails();

    void sendEmail(in EmailBean email);

    void registerListener(IEmailListener listener);

    void unregisterListener(IEmailListener listener);
}
