package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Inherited;
import java.util.Date;

/**
 * Created by Maksim on 12/5/2017.
 */

public class Order implements Parcelable{

    private int id;
    private Date cdate;
    private String name;
    private String picture;
    private float price;
    private int amount;
    private int user_id;
    private int category_id;
    private int address_id;

    public Order() {}

    protected Order(Parcel in) {
        this.id = in.readInt();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        this.name = in.readString();
        this.picture = in.readString();
        this.price = in.readLong();
        this.amount = in.readInt();
        this.user_id = in.readInt();
        this.category_id = in.readInt();
        this.address_id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.id);
        dest.writeLong(this.cdate != null ? this.cdate.getTime() : -1);
        dest.writeString(this.name);
        dest.writeString(this.picture);
        dest.writeFloat(this.price);
        dest.writeInt(this.amount);
        dest.writeInt(this.user_id);
        dest.writeInt(this.category_id);
        dest.writeInt(this.address_id);
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
