package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maksim on 12/5/2017.
 */

public class Address implements Parcelable{

    private int id;
    private int country_id;
    private int user_id;
    private String city;
    private String zip;
    private String street;
    private String house;
    private String flat;

    public Address() {}

    protected Address(Parcel in) {
        this.id = in.readInt();
        this.country_id = in.readInt();
        this.user_id = in.readInt();
        this.city = in.readString();
        this.zip = in.readString();
        this.street = in.readString();
        this.house = in.readString();
        this.flat = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.id);
        dest.writeInt(this.country_id);
        dest.writeInt(this.user_id);
        dest.writeString(this.city);
        dest.writeString(this.zip);
        dest.writeString(this.street);
        dest.writeString(this.house);
        dest.writeString(this.flat);
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
