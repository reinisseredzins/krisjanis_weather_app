
package com.example.pc.weatherapplication.weather_now;

import java.util.ArrayList;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Forecast {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("calctime")
    @Expose
    private Double calctime;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<ForecastListItem> list = new ArrayList<ForecastListItem>();

    /**
     * 
     * @return
     *     The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * 
     * @param cod
     *     The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * 
     * @return
     *     The calctime
     */
    public Double getCalctime() {
        return calctime;
    }

    /**
     * 
     * @param calctime
     *     The calctime
     */
    public void setCalctime(Double calctime) {
        this.calctime = calctime;
    }

    /**
     * 
     * @return
     *     The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     * 
     * @param cnt
     *     The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<ForecastListItem> getList() {
        return list;
    }

    /**
     * 
     * @param list
     *     The list
     */
    public void setList(java.util.List<ForecastListItem> list) {
        this.list = list;
    }

}
