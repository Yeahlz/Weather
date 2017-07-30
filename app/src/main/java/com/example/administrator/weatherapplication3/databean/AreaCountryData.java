package com.example.administrator.weatherapplication3.databean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */

public class AreaCountryData {

    @SerializedName("id")
    private String countryId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    @SerializedName("name")
    private  String countryName;
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @SerializedName("weather_id")
    private String weatherId;
    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

}
