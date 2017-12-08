package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Maksim on 12/5/2017.
 */
@Data
public class Country implements Parcelable{
    private int id;
    private String name;
    private String abbreviaion;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.abbreviaion);
    }

    public Country() {
    }

    protected Country(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.abbreviaion = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
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
