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

    private int id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private Role role;
    private int firstaccess;
    private int lastaccess;
    private int lastlogin;
    private int avatar;
    private Sex sex;
    private Date cdate;
    private Date bdate;
    private String description;
    private String citation;
    private Active isactive;
    private Bin bin;
    private String token;
    private Profile profile;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.phone);
        dest.writeParcelable(this.role, flags);
        dest.writeInt(this.firstaccess);
        dest.writeInt(this.lastaccess);
        dest.writeInt(this.lastlogin);
        dest.writeInt(this.avatar);
        dest.writeInt(this.sex == null ? -1 : this.sex.ordinal());
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeLong(this.bdate != null ? this.bdate.getTime() : -1);
        dest.writeString(this.description);
        dest.writeString(this.citation);
        dest.writeInt(this.isactive == null ? -1 : this.isactive.ordinal());
        dest.writeInt(this.bin == null ? -1 : this.bin.ordinal());
        dest.writeString(this.token);
        dest.writeInt(this.profile == null ? -1 : this.profile.ordinal());
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.email = in.readString();
        this.password = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.phone = in.readString();
        this.role = in.readParcelable(Role.class.getClassLoader());
        this.firstaccess = in.readInt();
        this.lastaccess = in.readInt();
        this.lastlogin = in.readInt();
        this.avatar = in.readInt();
        int tmpSex = in.readInt();
        this.sex = tmpSex == -1 ? null : Sex.values()[tmpSex];
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        long tmpBdate = in.readLong();
        this.bdate = tmpBdate == -1 ? null : new Date(tmpBdate);
        this.description = in.readString();
        this.citation = in.readString();
        int tmpIsactive = in.readInt();
        this.isactive = tmpIsactive == -1 ? null : Active.values()[tmpIsactive];
        int tmpBin = in.readInt();
        this.bin = tmpBin == -1 ? null : Bin.values()[tmpBin];
        this.token = in.readString();
        int tmpProfile = in.readInt();
        this.profile = tmpProfile == -1 ? null : Profile.values()[tmpProfile];
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

    public String getPassword(){ return password; }
    public String getEmail() { return email; }
}
