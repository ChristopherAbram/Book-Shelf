package com.bookshelf.data.collection;

import android.os.Parcel;
import android.os.Parcelable;

import com.bookshelf.data.FullUser;
import com.bookshelf.data.Item;

import java.util.ArrayList;

import lombok.Data;

@Data
public class FullUsers implements Parcelable {
    private ArrayList<FullUser> fullusers;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.fullusers);
    }

    protected FullUsers(Parcel in) {
        this.fullusers = in.createTypedArrayList(FullUser.CREATOR);
    }

    public static final Creator<FullUsers> CREATOR = new Creator<FullUsers>() {
        @Override
        public FullUsers createFromParcel(Parcel source) {
            return new FullUsers(source);
        }

        @Override
        public FullUsers[] newArray(int size) {
            return new FullUsers[size];
        }
    };
}
