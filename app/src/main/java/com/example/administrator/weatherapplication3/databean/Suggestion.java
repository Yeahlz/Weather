package com.example.administrator.weatherapplication3.databean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    public class Comfort{
        @SerializedName("txt")
        public String lifeSuggestion;
    }
    @SerializedName("cw")
    public CarWash carWash;
    public class CarWash{
        @SerializedName("txt")
        public String carWashSuggestion;
    }
    public Sport sport;
    public class Sport{
        @SerializedName("txt")
        public String sportSuggestion;
    }
}
