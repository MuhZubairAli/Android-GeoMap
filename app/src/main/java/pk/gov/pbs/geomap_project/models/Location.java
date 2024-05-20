package pk.gov.pbs.geomap_project.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

public class Location extends Table {
    @Nullable
    @Expose
    public Double Longitude;

    @Nullable
    @Expose
    public Double Latitude;

    @Nullable
    @Expose
    public Double Altitude;

    @Nullable
    @Expose
    public Float Accuracy;

    @Nullable
    @Expose
    public Float Speed;

    @Nullable
    @Expose
    public String LSProvider; //Location service provider ie, gps, network, locationPicker etc

    @Nullable
    @Expose
    public Long LTime; //Location time (time when location is set

    public Location() {
    }

    public Location(android.location.Location location){
        Longitude = location.getLongitude();
        Latitude = location.getLatitude();
        Altitude = location.getAltitude();
        Accuracy = location.getAccuracy();
        Speed = location.getSpeed();
        LSProvider = location.getProvider();
        LTime = location.getTime();
    }

    @Override
    public String toString() {
        return "Location{" +
                "Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                ", Altitude=" + Altitude +
                ", Accuracy=" + Accuracy +
                ", Speed=" + Speed +
                ", LSProvider='" + LSProvider + '\'' +
                ", LTime=" + LTime +
                '}';
    }
}
