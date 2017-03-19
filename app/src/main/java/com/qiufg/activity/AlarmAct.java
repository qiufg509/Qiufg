package com.qiufg.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiufg.Constants;
import com.qiufg.R;
import com.qiufg.receiver.NotificationReceiver;

import java.io.IOException;
import java.util.Calendar;

import butterknife.OnClick;

public class AlarmAct extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_alarm);

        if (getIntent().getIntExtra(Constants.EXTRA_DESTINATION, 0) == 2) {
            startMultiMedia();

            showDialog(null, getIntent().getStringExtra("alarm"), getCurrentFocus(), "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, false);
        }
    }

    private AlertDialog showDialog(String title, String message, View contentView,
                                   String positiveBtnText, String negativeBtnText,
                                   DialogInterface.OnClickListener positiveCallback,
                                   DialogInterface.OnClickListener negativeCallback,
                                   boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Holo_Light_ButtonBar_AlertDialog);
        builder.setTitle(title == null ? "提示" : title);
        if (message != null) {
            builder.setMessage(message);
        }
        if (contentView != null) {
            builder.setView(contentView);
        }
        if (positiveBtnText != null) {
            builder.setPositiveButton(positiveBtnText, positiveCallback);
        }
        if (negativeBtnText != null) {
            builder.setNegativeButton(negativeBtnText, negativeCallback);
        }
        builder.setCancelable(cancelable);
        return builder.show();
    }


    private void startMultiMedia() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
            mMediaPlayer.setLooping(true);
            try {
                mMediaPlayer.prepare();
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        mMediaPlayer.start();

        if (vibrator == null) {
            //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
            //第二个参数为重复次数，-1为不重复，0为一直震动
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
        }
        vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    @OnClick(R.id.alarm)
    public void setAlarm() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.setAction("com.qiufg.destination");
        intent.putExtra(Constants.EXTRA_DESTINATION, 2);
        intent.putExtra("alarm", "闹铃时间到");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 12);
        calendar.set(Calendar.MINUTE, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            API 19以后没有了重复设置，需要在闹铃事件触发之后重新设置闹铃
            //加入电池优化处理，休眠状态间隔相应的时间(hero8间隔5分钟)统一触发相关事件，相比setExact更省电
//            am.setWindow(int type, long windowStartMillis, long windowLengthMillis,PendingIntent operation);
            // type：闹钟类型 @{RTC可以通过修改手机时间触发闹钟事件;ELAPSED_REALTIME要通过真实时间的流逝，即使在休眠状态，时间也会被计算}
            // windowStartMillis：首次执行时间
            // windowLengthMillis：两次执行的间隔时间
            // operation：响应动作
            am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, /*calendar.getTimeInMillis()*/System.currentTimeMillis() + 50000, pendingIntent);
        } else {
//            am.setRepeating();//重复闹钟
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);//一次性闹钟
        }
    }
}
