package com.bookshelf.data.collection;

import android.os.Parcel;
import android.os.Parcelable;

import com.bookshelf.data.Item;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by Krzysztof on 10.12.2017.
 */
@Data
public class Countries implements Parcelable {
    private ArrayList<Item> countries;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.countries);
    }

    protected Countries(Parcel in) {
        this.countries = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Parcelable.Creator<Countries> CREATOR = new Parcelable.Creator<Countries>() {
        @Override
        public Countries createFromParcel(Parcel source) {
            return new Countries(source);
        }

        @Override
        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    };
}
