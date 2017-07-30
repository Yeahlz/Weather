package com.example.administrator.weatherapplication3.databean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */

public class Weather {
    public String status;
    public Basic basic;
    public Now now;
    public Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
