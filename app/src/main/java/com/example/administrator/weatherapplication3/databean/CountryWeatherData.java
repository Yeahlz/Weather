package com.example.administrator.weatherapplication3.databean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/15.
 */

public class CountryWeatherData {   //城市天气数据
    private  String date ;
    @SerializedName("txt")
    private  String weather;
    @SerializedName("max")
    private  String highTemperature;
    @SerializedName("min")
    private  String lowTemperature;
    private  String sport;
    @SerializedName("city")
    private  String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFlu() {
        return flu;
    }

    public void setFlu(String flu) {
        this.flu = flu;
    }

    private String flu;
    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(String highTemperature) {
        this.highTemperature = highTemperature;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(String lowTemperature) {
        this.lowTemperature = lowTemperature;
    }
}
