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

}
