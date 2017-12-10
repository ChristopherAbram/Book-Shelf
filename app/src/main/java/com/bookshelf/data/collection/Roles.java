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
public class Roles implements Parcelable {
    private ArrayList<Item> roles;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.roles);
    }

    protected Roles(Parcel in) {
        this.roles = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Parcelable.Creator<Roles> CREATOR = new Parcelable.Creator<Roles>() {
        @Override
        public Roles createFromParcel(Parcel source) {
            return new Roles(source);
        }

        @Override
        public Roles[] newArray(int size) {
            return new Roles[size];
        }
    };
}
