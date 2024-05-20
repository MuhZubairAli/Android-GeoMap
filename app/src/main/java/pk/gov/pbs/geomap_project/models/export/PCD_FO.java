package pk.gov.pbs.geomap_project.models.export;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

import org.osmdroid.util.GeoPoint;

public class PCD_FO extends Assignment {
    @Nullable
    @Expose
    public String VIL_MAUZA_DEH_MC_TC;
    @Nullable
    @Expose
    public String Province;
    @Nullable
    @Expose
    public String District;
    @Nullable
    @Expose
    public Integer Urban_Rural;
    @Nullable
    @Expose
    public String Tehseel;
    @Nullable
    @Expose
    public String FO_Office;
    @Nullable
    @Expose
    public String IsAssigned;
    @Nullable
    @Expose
    public String HeadName;

    @Nullable
    @Expose
    public String FatherName;

    @Nullable
    @Expose
    public String PhoneNumber;

    @Nullable
    @Expose
    public String Locality;

    @Nullable
    @Expose
    public String Address;


    @Nullable
    @Expose
    public String Remarks;


    public PCD_FO() {
    }

    public PCD_FO(@Nullable String VIL_MAUZA_DEH_MC_TC, @Nullable String province, @Nullable String district, @Nullable Integer urban_Rural, @Nullable String tehseel, @Nullable String FO_Office, @Nullable String isAssigned, @Nullable String headName, @Nullable String fatherName, @Nullable String phoneNumber, @Nullable String locality, @Nullable String address, @Nullable String remarks) {
        super();
        this.VIL_MAUZA_DEH_MC_TC = VIL_MAUZA_DEH_MC_TC;
        Province = province;
        District = district;
        Urban_Rural = urban_Rural;
        Tehseel = tehseel;
        this.FO_Office = FO_Office;
        IsAssigned = isAssigned;
        HeadName = headName;
        FatherName = fatherName;
        PhoneNumber = phoneNumber;
        Locality = locality;
        Address = address;
        Remarks = remarks;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    @Nullable
    public String getVIL_MAUZA_DEH_MC_TC() {
        return VIL_MAUZA_DEH_MC_TC;
    }

    public void setVIL_MAUZA_DEH_MC_TC(@Nullable String VIL_MAUZA_DEH_MC_TC) {
        this.VIL_MAUZA_DEH_MC_TC = VIL_MAUZA_DEH_MC_TC;
    }

    @Nullable
    public String getProvince() {
        return Province;
    }

    public void setProvince(@Nullable String province) {
        Province = province;
    }

    @Nullable
    public String getDistrict() {
        return District;
    }

    public void setDistrict(@Nullable String district) {
        District = district;
    }

    @Nullable
    public Integer getRegion() {
        return Urban_Rural;
    }

    public void setUrban_Rural(@Nullable Integer urban_Rural) {
        Urban_Rural = urban_Rural;
    }

    @Nullable
    public String getTehseel() {
        return Tehseel;
    }

    public void setTehseel(@Nullable String tehseel) {
        Tehseel = tehseel;
    }

    @Nullable
    public String getFO_Office() {
        return FO_Office;
    }

    public void setFO_Office(@Nullable String FO_Office) {
        this.FO_Office = FO_Office;
    }

    @Nullable
    public String getIsAssigned() {
        return IsAssigned;
    }

    public void setIsAssigned(@Nullable String isAssigned) {
        IsAssigned = isAssigned;
    }

    @Nullable
    public String getHeadName() {
        return HeadName;
    }

    public void setHeadName(@Nullable String headName) {
        HeadName = headName;
    }

    @Nullable
    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(@Nullable String fatherName) {
        FatherName = fatherName;
    }

    @Nullable
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @Nullable
    public String getAddress() {
        return Address;
    }

    public void setAddress(@Nullable String address) {
        Address = address;
    }

    @Nullable
    public String getLocality() {
        return Locality;
    }

    public void setLocality(@Nullable String locality) {
        Locality = locality;
    }

    public String getRemarks(){
        return Remarks;
    }

    public GeoPoint getPosition(){
        return new GeoPoint(Latitude, Longitude);
    }
}
