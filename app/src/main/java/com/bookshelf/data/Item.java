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
public class Item implements Parcelable {

    private Integer id;
    private String namepath;
    private String name;
    private String description;
    private String parameters;
    private String picture;
    private Date cdate;
    private Date edate;
    private Float price;
    private Integer amount;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("category_id")
    private Integer categoryId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.namepath);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.parameters);
        dest.writeString(this.picture);
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeLong(this.edate != null ? this.edate.getTime() : -1);
        dest.writeValue(this.price);
        dest.writeValue(this.amount);
        dest.writeValue(this.userId);
        dest.writeValue(this.categoryId);
    }

    protected Item(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.namepath = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.parameters = in.readString();
        this.picture = in.readString();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        long tmpEdate = in.readLong();
        this.edate = tmpEdate == -1 ? null : new Date(tmpEdate);
        this.price = (Float) in.readValue(Float.class.getClassLoader());
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
