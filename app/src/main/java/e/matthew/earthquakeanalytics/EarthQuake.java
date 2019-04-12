package e.matthew.earthquakeanalytics;
// Name                MATTHEW CARSON
// Student ID           S1507791
// Programme of Study   Computing
//
import android.os.Parcel;
import android.os.Parcelable;

public class EarthQuake  implements Parcelable {

    private String dateTime;
    private String location;
    private int depth;
    private String stat;
    private double magnitude;
    private double geolat;
    private double geolong;

    public EarthQuake() {
        dateTime = "";
        location = "";
        stat ="";
        depth = 0;
        magnitude = 0;
        geolat = 0;
        geolong = 0;
    }

    public EarthQuake(String parseddateTime, String parsedLocation, int parsedDepth, double parsedMagnitude, double parsedgeolat, double parsedgelong) {
        dateTime = parseddateTime;
        location = parsedLocation;
        depth = parsedDepth;
        magnitude = parsedMagnitude;
        geolat = parsedgeolat;
        geolong = parsedgelong;
    }

    protected EarthQuake(Parcel in) {
        dateTime = in.readString();
        location = in.readString();
        stat = in.readString();
        depth = in.readInt();
        magnitude = in.readDouble();
        geolat = in.readDouble();
        geolong = in.readDouble();
    }

    public static final Creator<EarthQuake> CREATOR = new Creator<EarthQuake>() {
        @Override
        public EarthQuake createFromParcel(Parcel in) {
            return new EarthQuake(in);
        }

        @Override
        public EarthQuake[] newArray(int size) {
            return new EarthQuake[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateTime);
        dest.writeString(location);
        dest.writeString(stat);
        dest.writeInt(depth);
        dest.writeDouble(magnitude);
        dest.writeDouble(geolat);
        dest.writeDouble(geolong);
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getStat() {
        return stat;
    }

    public int getDepth() {
        return depth;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getGeolat() {
        return geolat;
    }

    public double getGelong() {
        return geolong;
    }

    public void setDateTime(String newDateTime) {
        dateTime = newDateTime;
    }

    public void setLocation(String newLocation) {
        location = newLocation;
    }

    public void setStat(String stats) {
        stat = stats;
    }

    public void setDepth(int newDepth) {
        depth = newDepth;
    }

    public void setMagnitude(double newMagnitude) {
        magnitude = newMagnitude;
    }

    public void setGeolat(double newGeolat) {
        geolat = newGeolat;
    }

    public void setGeolong(double newGeolong) {
        geolong = newGeolong;
    }

    public String toString() {
        String earthobj;
        earthobj = location + " " + dateTime + " " +"Magnitude : "+ magnitude +" Depth  :  " + depth + "km";
        return earthobj;
    }
}