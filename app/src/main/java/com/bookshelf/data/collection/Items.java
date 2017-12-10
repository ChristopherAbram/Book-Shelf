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
public class Items implements Parcelable {
    private ArrayList<Item> items;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.items);
    }

    protected Items(Parcel in) {
        this.items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Parcelable.Creator<Items> CREATOR = new Parcelable.Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel source) {
            return new Items(source);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };
}
