package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maksim on 12/5/2017.
 */

public class Country implements Parcelable{
    private int id;
    private String name;
    private String abbreviaion;

    public Country(){ }

    protected Country(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.abbreviaion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.abbreviaion);
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
