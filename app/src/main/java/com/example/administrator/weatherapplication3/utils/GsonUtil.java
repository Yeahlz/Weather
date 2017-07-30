package com.example.administrator.weatherapplication3.utils;

import android.content.ContentValues;
import android.text.TextUtils;

import com.example.administrator.weatherapplication3.databean.AreaCountryData;
import com.example.administrator.weatherapplication3.databean.CityData;
import com.example.administrator.weatherapplication3.databean.ProvinceData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.administrator.weatherapplication3.activity.ChooseAreaFragment.sdb;


/**
 * Created by ${Learning_Yeachlz} on 2017/7/19.
 */
/**
 *
 * 解析城市信息并存入数据库
 */
public  class GsonUtil  {

       ContentValues values =new ContentValues();

    public  void parseProvinceData(String response){
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            List<ProvinceData>provinceDataList =gson.fromJson(response,new TypeToken<List<ProvinceData>>(){}.getType());
            for (int i=0;i<provinceDataList.size();i++){
                values.put("provinceId", provinceDataList.get(i).getProvinceId());
                values.put("provinceName",provinceDataList.get(i).getProvinceName());
                sdb.insert("Province", null, values);
                values.clear();
            }

        }

    }
    public boolean parseCityData(String response,String provinceId){
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            List<CityData>cityDataList =gson.fromJson(response, new TypeToken<List<CityData>>(){}.getType());
            for (int i=0;i<cityDataList.size();i++){
                values.put("cityId", cityDataList.get(i).getCityId());
                values.put("cityName",cityDataList.get(i).getCityName());
                values.put("provinceId",provinceId);
                sdb.insert("City", null, values);
                values.clear();
            }
            return true;
        }
        return false;
    }
    public boolean parseCountryData(String response,String cityId){
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            List<AreaCountryData>countryDataList=gson.fromJson(response,new TypeToken<List<AreaCountryData>>(){}.getType());
            for (int i=0;i<countryDataList.size();i++){
                values.put("countryId", countryDataList.get(i).getCountryId());
                values.put("countryName",countryDataList.get(i).getCountryName());
                values.put("cityId",cityId);
                values.put("weatherId",countryDataList.get(i).getWeatherId());
                sdb.insert("Country", null, values);
                values.clear();
            }
            return true;
        }
        return false;
    }

}
