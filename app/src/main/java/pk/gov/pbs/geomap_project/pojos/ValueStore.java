package pk.gov.pbs.geomap_project.pojos;

import androidx.annotation.NonNull;

public class ValueStore {
    @NonNull
    private String value;

    public ValueStore(@NonNull String val){
        setValue(val);
    }

    public ValueStore(boolean val) {
        setValue(val);
    }

    public ValueStore(int val){
        setValue(val);
    }

    public ValueStore(long val) {
        setValue(val);
    }

    public ValueStore(double val) {
        setValue(val);
    }

    public ValueStore(float val) {
        setValue(val);
    }

    public ValueStore(char val) {
        setValue(val);
    }

    public void setValue(String str){
        this.value = str;
    }

    public void setValue(boolean val){
        this.value = String.format("%b", val);
    }

    public void setValue(short val) {
        this.value = String.format("%d",val);
    }

    public void setValue(int val){
        this.value = String.format("%d",val);
    }

    public void setValue(long val) {
        this.value = String.format("%d", val);
    }

    public void setValue(double val) {
        this.value = String.format("%.3f", val);
    }

    public void setValue(float val) {
        this.value = String.format("%.3f", val);
    }

    public void setValue(char val) {
        this.value = String.format("%c", val);
    }

    @Override
    public String toString(){
        return value;
    }

    public boolean toBoolean(){
        return Boolean.parseBoolean(this.value);
    }

    public int toInt(){
        return Integer.parseInt(this.value);
    }

    public float toFloat(){
        return Float.parseFloat(this.value);
    }

    public double toDouble(){
        return Double.parseDouble(this.value);
    }

    public long toLong(){
        return Long.parseLong(this.value);
    }

    public char toChar(){
        return this.value.toCharArray()[0];
    }

    public boolean equalsIgnoreCase(ValueStore obj) {
        if(obj == null && this.value == null)
            return true;
        else if (obj == null)
            return false;
        return this.value.equalsIgnoreCase(obj.toString());
    }

    public boolean equalsIgnoreCase(String str) {
        if(str == null && this.value == null)
            return true;
        else if (str == null)
            return false;
        return this.value.equalsIgnoreCase(str);
    }

    //Null is not considered empty
    public boolean isEmpty(){
        return this.value.isEmpty();
    }

    public int length(){
        return this.value.length();
    }

    public Object tryCast(Class<?> clazz) {
        return clazz.cast(this.value);
    }
}
