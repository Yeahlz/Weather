package utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import databean.CountryData;
import databean.LocationData;


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

    public List<CountryData> weatherDataJsonUtil(String response) {
        List<CountryData>weatherDatalist = new ArrayList<>();
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                JSONObject jsonObject8 = jsonObject1.getJSONObject("basic");
                JSONArray jsonArray1 = jsonObject1.getJSONArray("daily_forecast");
                JSONObject jsonObject5 = jsonObject1.getJSONObject("suggestion");
                for (int i=0;i<3;i++) {
                    CountryData countryData = new CountryData();
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                    countryData.setDate(jsonObject2.getString("date"));
                    JSONObject jsonObject6 = jsonObject5.getJSONObject("sport");
                    JSONObject jsonObject7 = jsonObject5.getJSONObject("flu");
                    countryData.setFlu(jsonObject7.getString("txt"));
                    countryData.setSport(jsonObject6.getString("txt"));
                    countryData.setCityName(jsonObject8.getString("city"));
                    Log.d("AAAA", countryData.getDate());
                    JSONObject jsonObject4 =jsonObject2.getJSONObject("cond");
                    countryData.setWeather(jsonObject4.getString("txt_d"));
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("tmp");
                    countryData.setHighTemperature(jsonObject3.getString("max"));
                    countryData.setLowTemperature(jsonObject3.getString("min"));
                    weatherDatalist.add(countryData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return weatherDatalist;
    }
}