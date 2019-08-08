package com.qiufg.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.qiufg.Constants;
import com.qiufg.IManager;
import com.qiufg.R;
import com.qiufg.model.Person;
import com.qiufg.service.BindService;
import com.qiufg.service.ForegroundService;
import com.qiufg.service.StartService;
import com.qiufg.util.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceAct extends AppCompatActivity {

    @BindView(R.id.aidl)
    TextView mAidl;
    private boolean SERVICE_FOREGROUND = false;
    private boolean SERVICE_AIDL = false;

    private IManager iManager;
    private int tag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_service);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SERVICE_AIDL) {
            bindRemoteService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, StartService.class));
        if (SERVICE_FOREGROUND) {
            unbindService(mBindConnection);
            SERVICE_FOREGROUND = false;
        }
        if (SERVICE_AIDL) {
            unbindService(mAidlConnection);
            SERVICE_AIDL = false;
        }
    }

    @OnClick(R.id.start)
    public void start() {
        startService(new Intent(this, StartService.class));
    }

    @OnClick(R.id.bind)
    public void bind() {
        SERVICE_FOREGROUND = true;
        bindService(new Intent(this, BindService.class), mBindConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.foreground)
    public void foreground() {
        startService(new Intent(this, ForegroundService.class));
    }

    @OnClick(R.id.aidl)
    public void aidl() {
        if (!SERVICE_AIDL) {
            bindRemoteService();
            Toast.show(this, "当前与服务处于断开状态，正在尝试重连，请稍候再试");
        }
        if (iManager != null) {
            try {
                String args = getIntent().getBundleExtra(Constants.EXTRA_SERVICE_ACT).getString("money", "0.999");
                String money = iManager.switchMoney(args);
                Snackbar.make(mAidl, "switchMoney=" + money, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                Person person = new Person();
                person.setName("张三" + tag++);
                person.setAge(tag * 3 + tag / 2);
                iManager.addPerson(person);
                List<Person> list = iManager.getPerson();

                String response = "服务返回：\n\t";
                for (Person p : list) {
                    response += p.toString() + "\n\t";
                }
                Toast.show(this, response);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindRemoteService() {
        Intent aidlIntent = new Intent();
        aidlIntent.setAction("com.qiufg.remote");
        aidlIntent.setPackage(getPackageName());//Android5.0后隐式调用需要设置包名//此处设置为服务的包名，当前为同一个model里所以可以直接getPackageName，否则需要完整写出
        bindService(aidlIntent, mAidlConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mBindConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service != null && service instanceof BindService.MyBinder) {
                BindService.MyBinder binder = (BindService.MyBinder) service;
                binder.downLoad();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection mAidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SERVICE_AIDL = true;
            iManager = IManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            SERVICE_AIDL = false;
        }
    };
}
