package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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
    private String cdate;
    private String edate;
    private Float price;
    private Integer amount;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("category_id")
    private Integer categoryId;

    public String convertToWalletPrice(){
        int p = (int)(price * 100);
        return Integer.toString(p);
    }

    public static String priceToString(float price){
        return Float.toString(price);
    }

    public String priceToString(){
        return priceToString(price);
    }

    public float getTotalPrice(){
        return amount * price;
    }

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
        dest.writeString(this.cdate);
        dest.writeString(this.edate);
        dest.writeValue(this.price);
        dest.writeValue(this.amount);
        dest.writeValue(this.userId);
        dest.writeValue(this.categoryId);
    }

    public Item(){}

    protected Item(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.namepath = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.parameters = in.readString();
        this.picture = in.readString();
        this.cdate = in.readString();
        this.edate = in.readString();
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
