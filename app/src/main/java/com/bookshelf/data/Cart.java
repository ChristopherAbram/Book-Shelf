package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

/**
 * Created by Maksim on 12/5/2017.
 */
@Data
public class Cart implements Parcelable{

    private int id;
    private Date cdate;
    private int amount;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("item_id")
    private int itemId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeInt(this.amount);
        dest.writeInt(this.userId);
        dest.writeInt(this.itemId);
    }

    protected Cart(Parcel in) {
        this.id = in.readInt();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        this.amount = in.readInt();
        this.userId = in.readInt();
        this.itemId = in.readInt();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
