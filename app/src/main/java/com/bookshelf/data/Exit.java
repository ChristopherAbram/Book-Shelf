package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Krzysztof on 10.12.2017.
 */
@Data
public class Exit implements Parcelable {
    private String exit;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.exit);
    }

    protected Exit(Parcel in) {
        this.exit = in.readString();
    }

    public static final Parcelable.Creator<Exit> CREATOR = new Parcelable.Creator<Exit>() {
        @Override
        public Exit createFromParcel(Parcel source) {
            return new Exit(source);
        }

        @Override
        public Exit[] newArray(int size) {
            return new Exit[size];
        }
    };
}
