package pmf.novak101.visitedplacesmapspractice.shared;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItemPlace implements Parcelable {
    private String address;
    private double latitude;
    private double longitude;

    public ListItemPlace(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ListItemPlace(Parcel parcel) {
        this.address = parcel.readString();
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new ListItemPlace(source);
        }

        @Override
        public ListItemPlace[] newArray(int size) {
            return new ListItemPlace[size];
        }
    };
}
