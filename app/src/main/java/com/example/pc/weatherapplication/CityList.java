
package com.example.pc.weatherapplication;

import android.database.Cursor;

import com.example.pc.weatherapplication.database.CityListContract;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nm")
    @Expose
    private String nm;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("isFavorite")
    @Expose
    private String isFavorite;

    public CityList(Integer id, String nm, String lon, String countryCode, String lat, String isFavorite) {
        this.id = id;
        this.nm = nm;
        this.lon = lon;
        this.countryCode = countryCode;
        this.lat = lat;
        this.isFavorite = isFavorite;
    }

    public CityList(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndexOrThrow(CityListContract.CityEntry.COLUMN_ID));
        nm = cursor.getString(cursor.getColumnIndexOrThrow(CityListContract.CityEntry.COLUMN_NAME));
        lon = cursor.getString(cursor.getColumnIndexOrThrow(CityListContract.CityEntry.COLUMN_LON));
        lat = cursor.getString(cursor.getColumnIndexOrThrow(CityListContract.CityEntry.COLUMN_LAT));
        countryCode = cursor.getString(cursor.getColumnIndexOrThrow(CityListContract.CityEntry.COLUMN_CODE));
        isFavorite = cursor.getString(cursor.getColumnIndexOrThrow(CityListContract.CityEntry.IS_FAVORITE));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIsFavorite() {
        return countryCode;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

}
