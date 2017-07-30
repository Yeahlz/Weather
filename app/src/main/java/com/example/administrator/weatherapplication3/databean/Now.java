package com.example.administrator.weatherapplication3.databean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */

public class Now {
    @SerializedName("tmp")
    public String temperaturre;
    @SerializedName("cond")
    public More more;
    public  class More{
        @SerializedName("txt")
        public String nowWeather;
    }
}
