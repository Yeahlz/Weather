package com.example.administrator.weatherapplication3.servicetext;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.administrator.weatherapplication3.R;
import com.example.administrator.weatherapplication3.databean.Forecast;
import com.example.administrator.weatherapplication3.databean.Weather;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/15.
 */

public class WeatherService extends Service {

    List<Forecast> mweatherDataList;
    Weather mweather;

    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        mweatherDataList = new ArrayList<>();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    /**
     *
     * 更新天气数据
     */
    public void upDateWeather() {
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.item_rl_weather_form);
         remoteViews.setTextViewText(R.id.weather_form_tv_weather, mweatherDataList.get(0).more.forcastWeather);
        remoteViews.setTextViewText(R.id.weather_form_tv_lowtemperature,mweatherDataList.get(0).temperature.min + "℃");
        remoteViews.setTextViewText(R.id.weather_form_tv_hightemperature, mweatherDataList.get(0).temperature.max + "℃");
        remoteViews.setTextViewText(R.id.weather_form_tv_countryname, mweather.basic.cityName);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder.setContentTitle("weatherForm")
                .setContent(remoteViews)
                .setContentText("天气预报")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        startForeground(1, notification);
    }

    public class MyBinder extends Binder {
        public Service getService() {
            return WeatherService.this;
        }
    }
    /**
     *
     * 获取天气数据
     */
    public void getWeatherDatalist(List<Forecast> list) {
        mweatherDataList = list;
    }

    public void getWeatherObject(Weather weather){
       this.mweather=weather;
    }
}

