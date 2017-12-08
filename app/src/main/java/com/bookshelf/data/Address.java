package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Maksim on 12/5/2017.
 */
@Data
public class Address implements Parcelable{

    private int id;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("user_id")
    private int userId;
    private String city;
    private String zip;
    private String street;
    private String house;
    private String flat;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.countryId);
        dest.writeInt(this.userId);
        dest.writeString(this.city);
        dest.writeString(this.zip);
        dest.writeString(this.street);
        dest.writeString(this.house);
        dest.writeString(this.flat);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.id = in.readInt();
        this.countryId = in.readInt();
        this.userId = in.readInt();
        this.city = in.readString();
        this.zip = in.readString();
        this.street = in.readString();
        this.house = in.readString();
        this.flat = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
