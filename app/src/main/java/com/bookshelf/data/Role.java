package com.bookshelf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Krzysztof on 04.12.2017.
 */

@Data
public class Role implements Parcelable {

    public enum Level {
        ADMINISTRATOR,
        MERCHANT,
        CUSTOMER
    }

    public enum RoleName {
        ADMINISTRATOR("Administrator"),
        MERCHANT("Merchant"),
        CUSTOMER("Customer");

        private String name;
        RoleName(String n){
            name = n;
        }

        @Override
        public String toString(){
            return name;
        }
    }


    private Integer id;

    @SerializedName("access_level")
    private Integer accessLevel;

    private String name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.id);
        dest.writeValue(this.accessLevel);
    }

    protected Role(Parcel in) {
        this.name = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accessLevel = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Role> CREATOR = new Parcelable.Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel source) {
            return new Role(source);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };
}
