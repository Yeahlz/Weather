package com.example.administrator.weatherapplication3.databean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */

public class CityData {
    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    private String provinceId;
    @SerializedName("id")
    private String cityId;
    @SerializedName("name")
    private String cityName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
