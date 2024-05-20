package pk.gov.pbs.geomap_project.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

import pk.gov.pbs.database.annotations.NotNull;
import pk.gov.pbs.database.annotations.Unique;

public class BlockAssignment extends Table{
    @NotNull
    @Expose
    @Unique
    public String PCode;

    @NotNull
    @Expose
    public String EBCode;

    @Nullable
    @Expose
    public Integer Quarter;

    @Nullable
    @Expose
    @Unique
    public String Assignee;

    @Nullable
    @Expose
    public String Assigner;

    @Nullable
    @Expose
    public String DBegin; //Date Begin

    @Nullable
    @Expose
    public String DEnd; //Date End

    @Nullable
    @Expose
    public String DAssigned; // Date assigned

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
    public String Locality;

    @Nullable
    @Expose
    public String Address;

    public BlockAssignment() {
    }

    public BlockAssignment(String PCode, String EBCode, @Nullable Integer quarter, @Nullable String assignee, @Nullable String assigner, @Nullable String DBegin, @Nullable String DEnd, @Nullable String DAssigned, @Nullable String VIL_MAUZA_DEH_MC_TC, @Nullable String province, @Nullable String district, @Nullable Integer urban_Rural, @Nullable String tehseel, @Nullable String FO_Office, @Nullable String locality, @Nullable String address) {
        this.PCode = PCode;
        this.EBCode = EBCode;
        Quarter = quarter;
        Assignee = assignee;
        Assigner = assigner;
        this.DBegin = DBegin;
        this.DEnd = DEnd;
        this.DAssigned = DAssigned;
        this.VIL_MAUZA_DEH_MC_TC = VIL_MAUZA_DEH_MC_TC;
        Province = province;
        District = district;
        Urban_Rural = urban_Rural;
        Tehseel = tehseel;
        this.FO_Office = FO_Office;
        Locality = locality;
        Address = address;
    }
}
