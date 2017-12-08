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
public class Order implements Parcelable{

    private int id;
    private Date cdate;
    private String name;
    private String picture;
    private float price;
    private int amount;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("address_id")
    private int addressId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeString(this.name);
        dest.writeString(this.picture);
        dest.writeFloat(this.price);
        dest.writeInt(this.amount);
        dest.writeInt(this.userId);
        dest.writeInt(this.categoryId);
        dest.writeInt(this.addressId);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.id = in.readInt();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        this.name = in.readString();
        this.picture = in.readString();
        this.price = in.readFloat();
        this.amount = in.readInt();
        this.userId = in.readInt();
        this.categoryId = in.readInt();
        this.addressId = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
