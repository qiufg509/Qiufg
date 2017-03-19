package com.qiufg.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.qiufg.IManager;
import com.qiufg.model.Person;
import com.qiufg.util.Money;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author qiufg
 * Date 2017/3/13 16:43
 */

public class AIDLService extends Service {

    private List<Person> mPersons = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //鉴权，防止任意客户端调用
        if (checkCallingOrSelfPermission("com.qiufg.remote") == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mStub;
    }

    IManager.Stub mStub = new IManager.Stub() {
        @Override
        public String switchMoney(String money) throws RemoteException {
            try {
                return Money.switchMoney(money);
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        public List<Person> getPerson() throws RemoteException {
            return mPersons == null ? new ArrayList<Person>() : mPersons;
        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            if (mPersons == null) {
                mPersons = new ArrayList<>();
            }
            mPersons.add(person);
        }
    };

}
