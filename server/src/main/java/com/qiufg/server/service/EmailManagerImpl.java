package com.qiufg.server.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.qiufg.server.IEmailListener;
import com.qiufg.server.IEmailManager;
import com.qiufg.server.util.Logger;
import com.qiufg.template.bean.EmailBean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by qiufg on 2019/10/30 14:28.
 * <p>
 * Desc：Binder邮件读取、发送、监听
 */
public class EmailManagerImpl extends IEmailManager.Stub {

    private static final String TAG = "EmailManagerImpl";
    // 所有书籍 （自带异步锁）
    private CopyOnWriteArrayList<EmailBean> mEmailBeans = new CopyOnWriteArrayList<>();
    // 注销监听 系统专门提供跨进程删除 listener 接口的
    private RemoteCallbackList<IEmailListener> mRemoteCallbackList = new RemoteCallbackList<>();
    private final Handler mHandler;

    EmailManagerImpl() {
        HandlerThread handlerThread = new HandlerThread("send-email-worker");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
        mHandler.post(new SendEmailWorker());
    }

    @Override
    public List<EmailBean> getAllEmails() {
        return mEmailBeans;
    }

    @Override
    public void sendEmail(EmailBean email) {
        if (mEmailBeans != null) {
            if (mEmailBeans.size() > 30) {
                mEmailBeans.clear();
            }
            mEmailBeans.add(email);
            final int N = mRemoteCallbackList.beginBroadcast();
            for (int i = 0; i < N; i++) {
                IEmailListener emailListener = mRemoteCallbackList.getBroadcastItem(i);
                if (emailListener != null) {
                    try {
                        emailListener.onEmailReceived(email);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            mRemoteCallbackList.finishBroadcast();
        }
    }

    @Override
    public void registerListener(IEmailListener listener) {
        mRemoteCallbackList.register(listener);
        final int N = mRemoteCallbackList.beginBroadcast();
        mRemoteCallbackList.finishBroadcast();
        Logger.d(TAG, "registerListener, current size:" + N);
    }

    @Override
    public void unregisterListener(IEmailListener listener) {
        boolean success = mRemoteCallbackList.unregister(listener);
        if (success) {
            Logger.d(TAG, "unregister success.");
        } else {
            Logger.d(TAG, "not found, can not unregister.");
        }
        final int N = mRemoteCallbackList.beginBroadcast();
        mRemoteCallbackList.finishBroadcast();
        Logger.d(TAG, "unregisterListener, current size:" + N);
    }

    private class SendEmailWorker implements Runnable {

        private int mId = 0;

        @Override
        public void run() {
            EmailBean emailBean = new EmailBean();
            emailBean.setId(mId++);
            emailBean.setFrom("server@github.com");
            emailBean.setContent("服务自动发送");
            sendEmail(emailBean);
            mHandler.postDelayed(this, 10000);
        }
    }
}
