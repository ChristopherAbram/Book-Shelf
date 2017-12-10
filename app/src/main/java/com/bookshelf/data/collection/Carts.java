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
public class Carts implements Parcelable {
    private ArrayList<Item> carts;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.carts);
    }

    public Carts() {
    }

    protected Carts(Parcel in) {
        this.carts = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Parcelable.Creator<Carts> CREATOR = new Parcelable.Creator<Carts>() {
        @Override
        public Carts createFromParcel(Parcel source) {
            return new Carts(source);
        }

        @Override
        public Carts[] newArray(int size) {
            return new Carts[size];
        }
    };
}
