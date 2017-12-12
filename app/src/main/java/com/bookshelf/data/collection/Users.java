package com.bookshelf.data.collection;

import android.os.Parcel;
import android.os.Parcelable;

import com.bookshelf.data.User;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by Krzysztof on 10.12.2017.
 */
@Data
public class Users implements Parcelable {
    private ArrayList<User> users;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.users);
    }

    protected Users(Parcel in) {
        this.users = in.createTypedArrayList(User.CREATOR);
    }

    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel source) {
            return new Users(source);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
}
