
package com.example.pc.weatherapplication;

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

    public CityList(Integer id, String nm, String lon, String countryCode, String lat) {
        this.id = id;
        this.nm = nm;
        this.lon = lon;
        this.countryCode = countryCode;
        this.lat = lat;
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

}
