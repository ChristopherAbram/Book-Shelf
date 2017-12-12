package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class FullUser implements Parcelable {
    private Integer id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private Integer firstaccess;
    private Integer lastaccess;
    private Integer lastlogin;
    private Integer avatar;
    private Character sex;
    private String cdate;
    private String bdate;
    private String description;
    private Integer isactive;
    private Integer bin;
    private String token;
    private String country;
    private String city;
    private String zip;
    private String street;
    private String house;
    private String flat;
    @SerializedName("access_level")
    private Integer accessLevel;
    private String role;

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
        dest.writeValue(this.firstaccess);
        dest.writeValue(this.lastaccess);
        dest.writeValue(this.lastlogin);
        dest.writeValue(this.avatar);
        dest.writeSerializable(this.sex);
        dest.writeString(this.cdate);
        dest.writeString(this.bdate);
        dest.writeString(this.description);
        dest.writeValue(this.isactive);
        dest.writeValue(this.bin);
        dest.writeString(this.token);
        dest.writeString(this.country);
        dest.writeString(this.city);
        dest.writeString(this.zip);
        dest.writeString(this.street);
        dest.writeString(this.house);
        dest.writeString(this.flat);
        dest.writeValue(this.accessLevel);
        dest.writeString(this.role);
    }

    protected FullUser(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.email = in.readString();
        this.password = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.phone = in.readString();
        this.firstaccess = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastaccess = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastlogin = (Integer) in.readValue(Integer.class.getClassLoader());
        this.avatar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sex = (Character) in.readSerializable();
        this.cdate = in.readString();
        this.bdate = in.readString();
        this.description = in.readString();
        this.isactive = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bin = (Integer) in.readValue(Integer.class.getClassLoader());
        this.token = in.readString();
        this.country = in.readString();
        this.city = in.readString();
        this.zip = in.readString();
        this.street = in.readString();
        this.house = in.readString();
        this.flat = in.readString();
        this.accessLevel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.role = in.readString();
    }

    public static final Parcelable.Creator<FullUser> CREATOR = new Parcelable.Creator<FullUser>() {
        @Override
        public FullUser createFromParcel(Parcel source) {
            return new FullUser(source);
        }

        @Override
        public FullUser[] newArray(int size) {
            return new FullUser[size];
        }
    };
}
