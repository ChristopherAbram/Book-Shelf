package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import lombok.Data;

/**
 * Created by Krzysztof on 04.12.2017.
 */

@Data
public class User implements Parcelable {

    public enum Sex {
        MALE('M'),
        FEMALE('F');

        private char s = 'M';
        Sex(char sex){
            s = sex;
        }

        @Override
        public String toString(){
            return Character.toString(s);
        }
    }

    public enum Active {
        OFF,
        ON
    }

    public enum Bin {
        MARK_AS_NORMAL,
        MARK_AS_REMOVED
    }

    public enum Profile {
        INVISIBLE,
        VISIBLE
    }

    public User(){}

    private Integer id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private Integer roleId;
    private Integer firstaccess;
    private Integer lastaccess;
    private Integer lastlogin;
    private Integer avatar;
    private Character sex;
    private Date cdate;
    private Date bdate;
    private String description;
    private String citation;
    private Integer isactive;
    private Integer bin;
    private String token;
    private Integer profile;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.phone);
        dest.writeValue(this.roleId);
        dest.writeValue(this.firstaccess);
        dest.writeValue(this.lastaccess);
        dest.writeValue(this.lastlogin);
        dest.writeValue(this.avatar);
        dest.writeSerializable(this.sex);
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeLong(this.bdate != null ? this.bdate.getTime() : -1);
        dest.writeString(this.description);
        dest.writeString(this.citation);
        dest.writeValue(this.isactive);
        dest.writeValue(this.bin);
        dest.writeString(this.token);
        dest.writeValue(this.profile);
    }

    protected User(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.email = in.readString();
        this.password = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.phone = in.readString();
        this.roleId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstaccess = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastaccess = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastlogin = (Integer) in.readValue(Integer.class.getClassLoader());
        this.avatar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sex = (Character) in.readSerializable();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        long tmpBdate = in.readLong();
        this.bdate = tmpBdate == -1 ? null : new Date(tmpBdate);
        this.description = in.readString();
        this.citation = in.readString();
        this.isactive = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bin = (Integer) in.readValue(Integer.class.getClassLoader());
        this.token = in.readString();
        this.profile = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
