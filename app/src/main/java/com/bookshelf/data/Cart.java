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
public class Cart implements Parcelable {

    private Integer id;
    private Date cdate;
    private Integer amount;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("item_id")
    private Integer itemId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeValue(this.amount);
        dest.writeValue(this.userId);
        dest.writeValue(this.itemId);
    }

    protected Cart(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.itemId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
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
