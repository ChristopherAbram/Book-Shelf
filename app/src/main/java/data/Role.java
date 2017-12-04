package data;

import android.os.Parcel;
import android.os.Parcelable;

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


    private int id;
    private Level accessLevel;
    private RoleName name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.accessLevel == null ? -1 : this.accessLevel.ordinal());
        dest.writeInt(this.name == null ? -1 : this.name.ordinal());
    }

    protected Role(Parcel in) {
        this.id = in.readInt();
        int tmpAccessLevel = in.readInt();
        this.accessLevel = tmpAccessLevel == -1 ? null : Level.values()[tmpAccessLevel];
        int tmpName = in.readInt();
        this.name = tmpName == -1 ? null : RoleName.values()[tmpName];
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
