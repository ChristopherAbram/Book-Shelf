package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Maksim on 12/5/2017.
 */

public class Category implements Parcelable{

    private int id;
    private String namepath;
    private String name;
    private String description;
    private String picture;
    private Date cdate;
    private Date edate;
    private int user_id;
    private int category_id;

    public Category() {}

    protected Category(Parcel in) {
        this.id = in.readInt();
        this.namepath = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.picture = in.readString();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        long tmpEdate = in.readLong();
        this.edate = tmpEdate == -1 ? null : new Date(tmpEdate);
        this.user_id = in.readInt();
        this.category_id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.id);
        dest.writeString(this.namepath);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.picture);
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeLong(this.edate != null ? this.edate.getTime() : -1);
        dest.writeInt(this.user_id);
        dest.writeInt(this.category_id);
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
