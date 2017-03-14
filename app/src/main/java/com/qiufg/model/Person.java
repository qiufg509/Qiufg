package com.qiufg.model;//包名要和Person.aidl的一致

import android.os.Parcel;
import android.os.Parcelable;

import com.qiufg.model.base.Basebean;

/**
 * Description
 * Author qiufg
 * Date 2017/3/13 23:15
 */

public class Person extends Basebean implements Parcelable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     *
     * @param parcel parcel
     */
    public void readFromParcel(Parcel parcel) {
        this.name = parcel.readString();
        this.age = parcel.readInt();
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
