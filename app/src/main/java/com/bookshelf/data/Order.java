package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Maksim on 12/5/2017.
 */
@Data
public class Order implements Parcelable {

    private Integer id;
    private String cdate;
    private String name;
    private String picture;
    private Float price;
    private Integer amount;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("category_id")
    private Integer categoryId;
    @SerializedName("address_id")
    private Integer addressId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.cdate);
        dest.writeString(this.name);
        dest.writeString(this.picture);
        dest.writeValue(this.price);
        dest.writeValue(this.amount);
        dest.writeValue(this.userId);
        dest.writeValue(this.categoryId);
        dest.writeValue(this.addressId);
    }

    protected Order(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cdate = in.readString();
        this.name = in.readString();
        this.picture = in.readString();
        this.price = (Float) in.readValue(Float.class.getClassLoader());
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.addressId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
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
