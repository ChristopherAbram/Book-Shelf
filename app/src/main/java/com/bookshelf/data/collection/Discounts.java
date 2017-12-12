package com.bookshelf.data.collection;

import android.os.Parcel;
import android.os.Parcelable;

import com.bookshelf.data.Cart;
import com.bookshelf.data.Discount;
import com.bookshelf.data.Item;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by Krzysztof on 10.12.2017.
 */

@Data
public class Discounts implements Parcelable {
    private ArrayList<Discount> discounts;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.discounts);
    }

    protected Discounts(Parcel in) {
        this.discounts = in.createTypedArrayList(Discount.CREATOR);
    }

    public static final Parcelable.Creator<Discounts> CREATOR = new Parcelable.Creator<Discounts>() {
        @Override
        public Discounts createFromParcel(Parcel source) {
            return new Discounts(source);
        }

        @Override
        public Discounts[] newArray(int size) {
            return new Discounts[size];
        }
    };
}
