package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Maksim on 12/5/2017.
 */
@Data
public class Category implements Parcelable {

    private Integer id;
    private String namepath;
    private String name;
    private String description;
    private String picture;
    private String cdate;
    private String edate;
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
        dest.writeString(this.picture);
        dest.writeString(this.cdate);
        dest.writeString(this.edate);
        dest.writeValue(this.userId);
        dest.writeValue(this.categoryId);
    }

    protected Category(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.namepath = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.picture = in.readString();
        this.cdate = in.readString();
        this.edate = in.readString();
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
