package pk.gov.pbs.geomap_project.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Field;

import pk.gov.pbs.database.annotations.Default;
import pk.gov.pbs.database.annotations.PrimaryKey;
import pk.gov.pbs.geomap_project.pojos.ValueStore;
import pk.gov.pbs.utils.Constants;
import pk.gov.pbs.utils.SystemUtils;

public abstract class Table implements Serializable {
    @PrimaryKey
    @Expose
    @SerializedName("APID")
    public Long aid;

    @Nullable
    @Expose
    @SerializedName("SID")
    public Long sid;

    @Expose
    @SerializedName("APTSCreated")
    public Long ts_created;

    @Nullable
    @Expose
    @SerializedName("APTSUpdated")
    public Long ts_updated;

    @Nullable
    @Expose
    @SerializedName("IC")
    public String integrityCheck;

    @Default(value = "1")
    @Expose
    @SerializedName("Status")
    public Integer status;

    public Table(){
        this.ts_created = SystemUtils.getUnixTs();
    }

    public void set(String prop, @Nullable ValueStore value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getField(prop);

		if (value == null)
            field.set(this, null);
        else if (field.getType() == String.class)
            field.set(this, value.toString());
        else if (field.getType() == int.class || field.getType() == Integer.class)
            field.set(this, value.toInt());
        else if (field.getType() == long.class || field.getType() == Long.class)
            field.set(this, value.toLong());
        else if (field.getType() == double.class || field.getType() == Double.class)
            field.set(this, value.toDouble());
        else if (field.getType() == boolean.class || field.getType() == Boolean.class)
            field.set(this, value.toBoolean());
        else if (field.getType() == float.class || field.getType() == Float.class)
            field.set(this, value.toFloat());
        else if (field.getType() == char.class || field.getType() == Character.class)
            field.set(this, value.toChar());
    }

    public boolean set(String prop, @Nullable Object value) throws NoSuchFieldException, IllegalAccessException {
        if (hasField(prop)) {
            Field field = getClass().getField(prop);
            field.set(this, value);
            return true;
        }
        return false;
    }

    public ValueStore get(String prop) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getField(prop);
        if(field.get(this) != null)
            return new ValueStore(field.get(this).toString());
        return null;
    }

    public boolean hasField(String field){
        try {
            Field f = getClass().getField(field);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public boolean checkDataIntegrity(){
        if (Constants.DEBUG_MODE)
            return true;

        if(integrityCheck != null){
            String has = integrityCheck;
            integrityCheck = null;
            String is = SystemUtils.MD5(toString());
            integrityCheck = has;
            return has.equalsIgnoreCase(is);
        }

        return false;
    }

    public void setupDataIntegrity(){
        integrityCheck = null;
        integrityCheck = SystemUtils.MD5(toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            Field[] allFields = getClass().getFields();
            for (Field field : allFields){
                if(field.get(this) != null)
                    sb.append(field.get(this).toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
