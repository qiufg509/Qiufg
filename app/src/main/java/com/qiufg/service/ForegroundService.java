package com.qiufg.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import com.qiufg.Constants;
import com.qiufg.R;
import com.qiufg.receiver.NotificationReceiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ForegroundService extends Service {

    private static final Class[] mStartForegroundSignature = new Class[]{int.class, Notification.class};
    private static final Class[] mStopForegroundSignature = new Class[]{boolean.class};
    private Method startForeground;
    private Method stopForeground;
    private NotificationManager mNM;

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            startForeground = ForegroundService.class.getMethod("startForeground", mStartForegroundSignature);
            stopForeground = ForegroundService.class.getMethod("stopForeground", mStopForegroundSignature);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            startForeground = null;
            stopForeground = null;
        }
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.setAction("com.qiufg.destination");
        intent.putExtra(Constants.EXTRA_DESTINATION, 1);
        intent.putExtra("money", "89556000.001");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this)
                    .setTicker("您有一条新的消息")
                    .setContentTitle("通知的标题")
                    .setContentText("通知的内容")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .build();

        } else {
            //noinspection deprecation
            notification = new Notification(R.mipmap.ic_launcher, "您有一条新的消息", System.currentTimeMillis());
            //noinspection deprecation
            notification.largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            //noinspection deprecation
            notification.icon = R.mipmap.ic_launcher;
            notification.contentIntent = pendingIntent;
            notification.defaults = Notification.DEFAULT_SOUND;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }
        startForegroundCompat(1, notification);
    }

    private void startForegroundCompat(int id, Notification notification) {
        if (startForeground != null) {
            Object[] mStartForegroundArgs = new Object[2];
            mStartForegroundArgs[0] = id;
            mStartForegroundArgs[1] = notification;

            try {
                startForeground.invoke(this, mStartForegroundArgs);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            return;
        }
        mNM.notify(id, notification);
    }

    // 以兼容性方式停止前台服务
    private void stopForegroundCompat(int id) {
        if (stopForeground != null) {
            Object[] mStopForegroundArgs = new Object[1];
            mStopForegroundArgs[0] = Boolean.TRUE;

            try {
                stopForeground.invoke(this, (Object[]) mStopForegroundArgs);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return;
        }

        //  在 setForeground 之前调用 cancel，因为我们有可能在取消前台服务之后
        //  的那一瞬间被kill掉。这个时候 notification 便永远不会从通知一栏移除
        mNM.cancel(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForegroundCompat(1);
    }
}
