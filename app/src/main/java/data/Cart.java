package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Maksim on 12/5/2017.
 */

public class Cart implements Parcelable{

    private int id;
    private Date cdate;
    private int amount;
    private int user_id;
    private int item_id;

    public Cart(){}

    protected Cart(Parcel in) {
        this.id = in.readInt();
        long tmpCdate = in.readLong();
        this.cdate = tmpCdate == -1 ? null : new Date(tmpCdate);
        this.amount = in.readInt();
        this.user_id = in.readInt();
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
        dest.writeInt(this.amount);
        dest.writeInt(this.user_id);
        dest.writeInt(this.item_id);
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
