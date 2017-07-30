package com.example.administrator.weatherapplication3.utils;

import com.example.administrator.weatherapplication3.databean.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */
/**
 *
 * 解析天气信息
 */
public class WeatherUtil {
    public static Weather paresonWeather(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray =jsonObject.getJSONArray("HeWeather");
            String weatherContent =jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
