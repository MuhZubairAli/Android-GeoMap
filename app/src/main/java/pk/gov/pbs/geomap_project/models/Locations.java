package pk.gov.pbs.geomap_project.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

import pk.gov.pbs.database.annotations.NotNull;

public class Locations extends Table{
    @Nullable
    @Expose
    public String EBCode;

    @NotNull
    @Expose
    public String RefId; //Note text

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
    public String Source; //Location service provider ie, gps, network, locationPicker etc

    @Nullable
    @Expose
    public Long LocationTime; //Location time (time when location is set

    @Nullable
    @Expose
    public Boolean isInsideBlock; //0=no, 1=true

    @Nullable
    @Expose
    public String ExternalGPSLatitude;

    @Nullable
    @Expose
    public String ExternalGPSLongitude;

    public Locations() {
    }

    public Locations(@Nullable String EBCode, String refId, @Nullable Double longitude, @Nullable Double latitude, @Nullable Double altitude, @Nullable Float accuracy, @Nullable String source, @Nullable Long locationTime, @Nullable Boolean isInsideBlock, @Nullable String externalGPSLatitude, @Nullable String externalGPSLongitude) {
        this.EBCode = EBCode;
        RefId = refId;
        Longitude = longitude;
        Latitude = latitude;
        Altitude = altitude;
        Accuracy = accuracy;
        Source = source;
        LocationTime = locationTime;
        this.isInsideBlock = isInsideBlock;
        ExternalGPSLatitude = externalGPSLatitude;
        ExternalGPSLongitude = externalGPSLongitude;
    }
}
