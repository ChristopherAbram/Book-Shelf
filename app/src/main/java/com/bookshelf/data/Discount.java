package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Maksim on 12/5/2017.
 */
@Data
public class Discount implements Parcelable {

    private Integer id;
    private String cdate;
    private String name;
    private Integer amount;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("category_id")
    private Integer categoryId;
    @SerializedName("item_id")
    private Integer itemId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.cdate);
        dest.writeString(this.name);
        dest.writeValue(this.amount);
        dest.writeValue(this.userId);
        dest.writeValue(this.categoryId);
        dest.writeValue(this.itemId);
    }

    protected Discount(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cdate = in.readString();
        this.name = in.readString();
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.itemId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Discount> CREATOR = new Parcelable.Creator<Discount>() {
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
