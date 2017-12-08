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
public class Discount implements Parcelable{

    private int id;
    private Date cdate;
    private String name;
    private int amount;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("category_id")
    private int categoryId;
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
        dest.writeString(this.name);
        dest.writeInt(this.amount);
        dest.writeInt(this.userId);
        dest.writeInt(this.categoryId);
        dest.writeInt(this.itemId);
    }

    public Discount() {
    }

    protected Discount(Parcel in) {
        this.id = in.readInt();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        this.name = in.readString();
        this.amount = in.readInt();
        this.userId = in.readInt();
        this.categoryId = in.readInt();
        this.itemId = in.readInt();
    }

    public static final Creator<Discount> CREATOR = new Creator<Discount>() {
        @Override
        public Discount createFromParcel(Parcel source) {
            return new Discount(source);
        }

        @Override
        public Discount[] newArray(int size) {
            return new Discount[size];
        }
    };
}
