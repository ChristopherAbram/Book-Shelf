package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Maksim on 12/5/2017.
 */

public class Discount implements Parcelable{

    private int id;
    private Date cdate;
    private String name;
    private int amount;
    private int user_id;
    private int category_id;
    private int item_id;

    public Discount(){}

    protected Discount(Parcel in) {
        this.id = in.readInt();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        this.name = in.readString();
        this.amount = in.readInt();
        this.user_id = in.readInt();
        this.category_id = in.readInt();
        this.item_id = in.readInt();
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
        dest.writeInt(this.amount);
        dest.writeInt(this.user_id);
        dest.writeInt(this.category_id);
        dest.writeInt(this.item_id);
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
