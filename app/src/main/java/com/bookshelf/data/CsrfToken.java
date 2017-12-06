package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Krzysztof on 04.12.2017.
 */

@Data
public class CsrfToken implements Parcelable {

    @SerializedName("csrf_token")
    private String token;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
    }

    public CsrfToken() {
    }

    protected CsrfToken(Parcel in) {
        this.token = in.readString();
    }

    public static final Parcelable.Creator<CsrfToken> CREATOR = new Parcelable.Creator<CsrfToken>() {
        @Override
        public CsrfToken createFromParcel(Parcel source) {
            return new CsrfToken(source);
        }

        @Override
        public CsrfToken[] newArray(int size) {
            return new CsrfToken[size];
        }
    };
}
