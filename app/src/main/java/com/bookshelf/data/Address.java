package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Maksim on 12/5/2017.
 */
@Data
public class Address implements Parcelable {

    private Integer id;
    @SerializedName("country_id")
    private Integer countryId;
    @SerializedName("user_id")
    private Integer userId;
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
        dest.writeValue(this.id);
        dest.writeValue(this.countryId);
        dest.writeValue(this.userId);
        dest.writeString(this.city);
        dest.writeString(this.zip);
        dest.writeString(this.street);
        dest.writeString(this.house);
        dest.writeString(this.flat);
    }

    protected Address(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.countryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.city = in.readString();
        this.zip = in.readString();
        this.street = in.readString();
        this.house = in.readString();
        this.flat = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
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
