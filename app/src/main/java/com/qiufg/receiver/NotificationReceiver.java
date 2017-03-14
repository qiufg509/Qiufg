package com.qiufg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qiufg.Constants;
import com.qiufg.activity.MainAct;
import com.qiufg.activity.ServiceAct;
import com.qiufg.util.SystemUtils;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断app进程是否存活
        if (SystemUtils.isAppAlive(context, "com.qiufg")) {
            //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
            //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
            //ServiceAct,再按Back键就不会返回MainAct了。所以在启动ServiceAct前，要先启动MainAct。
            Intent mainIntent = new Intent(context, MainAct.class);
            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
            //如果Task栈不存在MainActivity实例，则在栈顶创建
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent detailIntent = new Intent(context, ServiceAct.class);
            Bundle bundle = new Bundle();
            bundle.putString("money", "89556000.001");
            detailIntent.putExtra(Constants.EXTRA_SERVICE_ACT, bundle);

            Intent[] intents = {mainIntent, detailIntent};
            context.startActivities(intents);
        } else {
            //如果app进程已经被杀死，先重新启动app，将ServiceAct的启动参数传入Intent中，参数经过
            //SplashActivity传入MainAct，此时app的初始化已经完成，在MainAct中就可以根据传入参数跳转到DetailActivity中去了
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("com.qiufg");
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            Bundle bundle = new Bundle();
            bundle.putString("money", "89556000.001");
            launchIntent.putExtra(Constants.EXTRA_SERVICE_ACT, bundle);
            context.startActivity(launchIntent);
        }
    }
}
