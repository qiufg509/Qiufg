package com.qiufg.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qiufg.IManager;
import com.qiufg.model.Person;

import java.util.List;

public class ScrollingAct extends AppCompatActivity {

    private IManager iManager;
    private boolean mBinded = false;

    private int tag = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mBinded) {
                    bindRemoteService();
                    Snackbar.make(view, "当前与服务处于断开状态，正在尝试重连，请稍候再试", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (iManager != null) {
                    try {
                        String money = iManager.switchMoney("480034503.26");

                        Person person = new Person();
                        person.setName("王五" + tag++);
                        person.setAge(tag * 2 + tag / 3);
                        iManager.addPerson(person);
                        List<Person> list = iManager.getPerson();

                        String response = "服务返回：\n\t";
                        for (Person p : list) {
                            response += p.toString() + "\n\t";
                        }
                        Toast.makeText(ScrollingAct.this, response, Toast.LENGTH_LONG).show();
                        Snackbar.make(view, "money=" + money, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBinded) {
            bindRemoteService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinded) {
            unbindService(mConnection);
            mBinded = false;
        }
    }

    private void bindRemoteService() {
        Intent intent = new Intent();
        intent.setAction("com.qiufg.IMyAidlInterface");
        intent.setPackage("com.qiufg");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinded = true;
            iManager = IManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinded = false;
        }
    };
}
