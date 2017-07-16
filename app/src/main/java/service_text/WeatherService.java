package service_text;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.weatherapplication3.R;

import java.util.ArrayList;
import java.util.List;

import databean.CountryData;

import static com.example.administrator.weatherapplication3.WeatherActivity.GETDATA;


/**
 * Created by Administrator on 2017/7/15.
 */

public class WeatherService extends Service {

    List<CountryData> weatherDataList ;

    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
       weatherDataList = new ArrayList<>();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);  //闹钟机制
        Intent intent1 = new Intent(this, WeatherService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent1, 0);
        long currenttime = SystemClock.elapsedRealtime() + 1000 * 60 * 60 * 6;
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, currenttime, pendingIntent);
        upDateWeather();
        return super.onStartCommand(intent, flags, startId);
    }

    public void upDateWeather() {   //更新天气数据
        Intent intent = new Intent(GETDATA);   //发送广播更新天气
        sendBroadcast(intent);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.weather_form);
        remoteViews.setTextViewText(R.id.tv_weather, weatherDataList.get(0).getWeather());
        Log.d("AAA",weatherDataList.get(0).getWeather());
        remoteViews.setTextViewText(R.id.tv_lowtemperature, weatherDataList.get(0).getLowTemperature() + "℃");
        remoteViews.setTextViewText(R.id.tv_hightemperature, weatherDataList.get(0).getHighTemperature() + "℃");
        remoteViews.setTextViewText(R.id.tv_countryname, weatherDataList.get(0).getCityName());
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

    public void getWeatherDatalist(List<CountryData>list) {  ///获取 activity 数据
        weatherDataList = list;
    }

}