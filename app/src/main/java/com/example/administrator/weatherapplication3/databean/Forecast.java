package com.example.administrator.weatherapplication3.databean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */

public class Forecast {
    public String date;
    @SerializedName("tmp")
    public Temperature temperature;
    public class Temperature{
        public String max;
        public String min;
    }
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt_d")
        public String forcastWeather;
    }
}
