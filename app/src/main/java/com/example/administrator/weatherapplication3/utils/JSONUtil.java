package com.example.administrator.weatherapplication3.utils;

import android.text.TextUtils;

import com.example.administrator.weatherapplication3.databean.CountryWeatherData;
import com.example.administrator.weatherapplication3.databean.LocationData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/15.
 */
public class JSONUtil {
    public List<LocationData> locationDataJson(String response) {  //数据解析
        List<LocationData> locationDataList = new ArrayList<>();
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray Location = new JSONArray(response);
                for (int i = 0; i < Location.length(); i++) {
                    JSONObject jsonObject = Location.getJSONObject(i);
                    LocationData locationData = new LocationData();
                    locationData.setId(jsonObject.getString("id"));
                    locationData.setName(jsonObject.getString("name"));
                    locationDataList.add(locationData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locationDataList;
    }

    public List<String> weatherDataJson(String response) {
        List<String> CountryDataList = new ArrayList<>();
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray Location = new JSONArray(response);
                for (int i = 0; i < Location.length(); i++) {
                    JSONObject jsonObject = Location.getJSONObject(i);
                    CountryDataList.add(jsonObject.getString("weather_id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return CountryDataList;
    }

    public List<CountryWeatherData> weatherDataJsonUtil(String response) {
        List<CountryWeatherData>weatherDatalist = new ArrayList<>();
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                JSONObject jsonObject8 = jsonObject1.getJSONObject("basic");
                JSONArray jsonArray1 = jsonObject1.getJSONArray("daily_forecast");
                JSONObject jsonObject5 = jsonObject1.getJSONObject("suggestion");
                for (int i=0;i<3;i++) {
                    CountryWeatherData countryWeatherData = new CountryWeatherData();
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                    countryWeatherData.setDate(jsonObject2.getString("date"));
                    JSONObject jsonObject6 = jsonObject5.getJSONObject("sport");
                    JSONObject jsonObject7 = jsonObject5.getJSONObject("flu");
                    countryWeatherData.setFlu(jsonObject7.getString("txt"));
                    countryWeatherData.setSport(jsonObject6.getString("txt"));
                    countryWeatherData.setCityName(jsonObject8.getString("city"));
                    JSONObject jsonObject4 =jsonObject2.getJSONObject("cond");
                    countryWeatherData.setWeather(jsonObject4.getString("txt_d"));
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("tmp");
                    countryWeatherData.setHighTemperature(jsonObject3.getString("max"));
                    countryWeatherData.setLowTemperature(jsonObject3.getString("min"));
                    weatherDatalist.add(countryWeatherData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return weatherDatalist;
    }
}