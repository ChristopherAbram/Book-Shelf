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
public class Orders implements Parcelable {
    private ArrayList<Item> orders;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.orders);
    }

    protected Orders(Parcel in) {
        this.orders = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Parcelable.Creator<Orders> CREATOR = new Parcelable.Creator<Orders>() {
        @Override
        public Orders createFromParcel(Parcel source) {
            return new Orders(source);
        }

        @Override
        public Orders[] newArray(int size) {
            return new Orders[size];
        }
    };
}
