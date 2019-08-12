package com.qiufg.mvp.module.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fengguang.qiu on 2019/3/20 14:55.
 * <p>
 * Desc：用户信息
 */
@Entity
public class UserInfo implements Parcelable {
    /**
     * uid : 253601808
     * nickname : 小乔
     * gender : 1
     * age : 22
     * avatar : http://img.chengshiapp.com/thumbnail/avatar/2/95/295E9240-81CF-3B1A-B7D6-3DA26827ACAD.jpg
     */

    @Id
    private long uid;
    private String nickname;
    private int gender;
    private int age;
    private String avatar;
    @Convert(converter = MeetCityConverter.class, columnType = String.class)
    private List<MeetCityListBean> meetCityList;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<MeetCityListBean> getMeetCityList() {
        return meetCityList;
    }

    public void setMeetCityList(List<MeetCityListBean> meetCityList) {
        this.meetCityList = meetCityList;
    }

    public static class MeetCityListBean implements Parcelable {
        /**
         * cityId : 110000
         * cityName : 北京市
         */

        private String cityId;
        private String cityName;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cityId);
            dest.writeString(this.cityName);
        }

        public MeetCityListBean() {
        }

        protected MeetCityListBean(Parcel in) {
            this.cityId = in.readString();
            this.cityName = in.readString();
        }

        public static final Creator<MeetCityListBean> CREATOR = new Creator<MeetCityListBean>() {
            @Override
            public MeetCityListBean createFromParcel(Parcel source) {
                return new MeetCityListBean(source);
            }

            @Override
            public MeetCityListBean[] newArray(int size) {
                return new MeetCityListBean[size];
            }
        };
    }

    public static class MeetCityConverter implements PropertyConverter<List<MeetCityListBean>, String> {

        @Override
        public List<MeetCityListBean> convertToEntityProperty(String databaseValue) {
            Type type = new TypeToken<List<MeetCityListBean>>() {
            }.getType();
            return new Gson().fromJson(databaseValue, type);
        }

        @Override
        public String convertToDatabaseValue(List<MeetCityListBean> entityProperty) {
            Type type = new TypeToken<List<MeetCityListBean>>() {
            }.getType();
            return new Gson().toJson(entityProperty, type);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeString(this.nickname);
        dest.writeInt(this.gender);
        dest.writeInt(this.age);
        dest.writeString(this.avatar);
        dest.writeTypedList(this.meetCityList);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.uid = in.readLong();
        this.nickname = in.readString();
        this.gender = in.readInt();
        this.age = in.readInt();
        this.avatar = in.readString();
        this.meetCityList = in.createTypedArrayList(MeetCityListBean.CREATOR);
    }

    @Generated(hash = 1795783312)
    public UserInfo(long uid, String nickname, int gender, int age, String avatar,
            List<MeetCityListBean> meetCityList) {
        this.uid = uid;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.avatar = avatar;
        this.meetCityList = meetCityList;
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
