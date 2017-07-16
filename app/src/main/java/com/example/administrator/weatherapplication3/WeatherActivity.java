package com.example.administrator.weatherapplication3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import adapter.LocationAdapter;
import adapter.WeatherAdapter;
import databean.CountryData;
import databean.LocationData;
import utils.HttpUtils;
import utils.JSONUtil;
import service_text.WeatherService;

public class WeatherActivity extends AppCompatActivity {

    public static final String GETDATA = "GETDATA ";                  //  一些地址常量
    public static final String PROVINCE = "http://guolin.tech/api/china";
    public static final String WeatherURL1 = "&key=83c091f8f3de49c3a54a638285baa1a1";
    public static final String WeatherURL = "http://guolin.tech/api/weather?cityid=";
    String url = "http://guolin.tech/api/weather?cityid=CN101280101&key=83c091f8f3de49c3a54a638285baa1a1";

    private final int provinceFlag = 1;          // 根据不同 flag 选择显示省市县
    private final int cityFlag= 2;
    private final int countryFlag = 3;
    private int locationFlag = 2;

    String cityUrl;                // 省市县 url 地址
    String countryUrl;
    String countryWeatherUrl;
    String data;

    ListView locationListView;
    ListView weatherListView;
    LocationAdapter locationAdapter;
    WeatherAdapter weatherAdapter;
    ImageView iv_addcity;
    ImageView bt_back;
    TextView tv_flu;
    TextView tv_sport;
    TextView tv_time;
    TextView tv_country;
    TextView tv_date;
    TextView tv_lowTemperature;
    TextView tv_highTemperature;
    TextView tv_weather;

    int time = 0;
    boolean flag = false;
    boolean areaFlag;

    JSONUtil jsonUtil = new JSONUtil();
    List<LocationData> locationDataList ;
    List<CountryData>weatherDatalist ;
    List<String> CountryDataList;
    WeatherService weatherService;

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {    //更新数据并显示
            Bundle bundle = msg.getData();
            data = bundle.getString("Data");
            if(areaFlag){   //判断当前是否是更新城市
                locationDataList = jsonUtil.locationDataJson(data);
                locationAdapter = new LocationAdapter(WeatherActivity.this, locationDataList);
                locationListView.setAdapter(locationAdapter);
            }
            time = time + 1;
            if ((time == 2) || (flag == true)) {   // 判断是否是更新天气
                weatherDatalist.clear();
                weatherDatalist = jsonUtil.weatherDataJsonUtil(data);
                tv_country.setText(weatherDatalist.get(0).getCityName());
                tv_date.setText(weatherDatalist.get(0).getDate());
                tv_sport.setText("活动建议:"+weatherDatalist.get(0).getSport());
                tv_lowTemperature.setText(weatherDatalist.get(0).getLowTemperature() + "℃");
                tv_flu.setText("生活建议:"+weatherDatalist.get(0).getFlu());
                tv_highTemperature.setText(weatherDatalist.get(0).getHighTemperature() + "℃");
                tv_weather.setText(weatherDatalist.get(0).getWeather());
                weatherAdapter = new WeatherAdapter(WeatherActivity.this,weatherDatalist);
                weatherListView.setAdapter(weatherAdapter);
                flag = false;
                weatherService.getWeatherDatalist(weatherDatalist);  //给服务传递数据
                Intent intent = new Intent(WeatherActivity.this,WeatherService.class);
                startService(intent);    //再次开启服务调用 onStartCommand 方法
            }
        }
    };

    public class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            weatherService = (WeatherService) ((WeatherService.MyBinder)service).getService();  //取得 service 对象
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    public class SendDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            weatherService.getWeatherDatalist(weatherDatalist);   //传递数据
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        LocationData locationData = new LocationData();
        CountryData countryData = new CountryData();
        locationData.setName("a");
        countryData.setCityName("a");
        locationDataList = new ArrayList<>();
        weatherDatalist = new ArrayList<>();
        locationDataList.add(locationData);
        weatherDatalist.add(countryData);

        upDateTime();//添加了时间 更新时间

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_lowTemperature = (TextView) findViewById(R.id.tv_lowtemperature);
        tv_highTemperature = (TextView) findViewById(R.id.tv_hightemperature);
        tv_country = (TextView) findViewById(R.id.tv_countryname);
        tv_weather = (TextView) findViewById(R.id.tv_weather);
        tv_sport=(TextView)findViewById(R.id.tv_sport);
        tv_flu =(TextView)findViewById(R.id.tv_flu);
        tv_time = (TextView) findViewById(R.id.tv_time);

        sendMessage(PROVINCE);    //请求省份数据
        locationListView = (ListView) findViewById(R.id.lv_location);
        weatherListView = (ListView) findViewById(R.id.lv_weather);
        locationListView.setVisibility(View.GONE);
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //根据点击事件选择显示省市县
                switch (locationFlag) {
                    case cityFlag:
                        bt_back.setVisibility(View.VISIBLE);
                        cityUrl = PROVINCE + "/" + locationDataList.get(position).getId();
                        locationDataList.clear();
                        sendMessage(cityUrl);
                        locationFlag = countryFlag;
                        locationAdapter.notifyDataSetChanged();
                        break;
                    case countryFlag:
                        countryUrl = cityUrl + "/" + locationDataList.get(position).getId();
                        locationDataList.clear();
                        sendMessage(countryUrl);
                        locationFlag = countryFlag + 1;
                        locationAdapter.notifyDataSetChanged();
                        break;
                    case countryFlag + 1:
                        viewVISIBLE();
                        CountryDataList = jsonUtil.weatherDataJson(data);
                        countryWeatherUrl = WeatherURL + CountryDataList.get(position) + WeatherURL1;
                        sendMessage( countryWeatherUrl);
                        locationListView.setVisibility(View.GONE);
                        bt_back.setVisibility(View.GONE);
                        flag = true;
                        locationFlag = 2;
                        areaFlag = false;
                    default:
                        break;

                }
            }
        });
        sendMessage( url); //请求天气数据
        iv_addcity = (ImageView) findViewById(R.id.iv_addcity);
        iv_addcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //显示省市县列表
                areaFlag = true;
                viewGone();
                locationListView.setVisibility(View.VISIBLE);
                locationDataList.clear();
                sendMessage(PROVINCE);
            }
        });

        bt_back = (ImageView) findViewById(R.id.bt_back);
        bt_back.setVisibility(View.GONE);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //根据返回键显示省市县列表
                locationDataList.clear();
                switch (locationFlag - 2) {
                    case provinceFlag:
                        bt_back.setVisibility(View.GONE);
                        sendMessage(PROVINCE);
                        locationFlag = locationFlag - 1;
                        break;
                    case cityFlag:
                        sendMessage(cityUrl);
                        locationFlag = locationFlag - 1;
                        break;
                    default:
                        break;
                }
                locationAdapter.notifyDataSetChanged();
            }
        });

        MyServiceConnection connection = new MyServiceConnection();
        Intent intent = new Intent(WeatherActivity.this,WeatherService.class);
        bindService(intent,connection,BIND_AUTO_CREATE); //连接 service

        IntentFilter intentFilter = new IntentFilter();  //注册广播
        intentFilter.addAction(GETDATA);
        SendDataReceiver sendDataReceiver =new SendDataReceiver();
        registerReceiver(sendDataReceiver,intentFilter);
    }

    public void viewGone() {   // 控件消失
        weatherListView.setVisibility(View.GONE);
        tv_lowTemperature.setVisibility(View.GONE);
        tv_highTemperature.setVisibility(View.GONE);
        tv_date.setVisibility(View.GONE);
        iv_addcity.setVisibility(View.GONE);
        tv_weather.setVisibility(View.GONE);
        tv_country.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
        tv_flu.setVisibility(View.GONE);
        tv_sport.setVisibility(View.GONE);
    }

    public void viewVISIBLE() {   //控件重现
        weatherListView.setVisibility(View.VISIBLE);
        tv_lowTemperature.setVisibility(View.VISIBLE);
        tv_highTemperature.setVisibility(View.VISIBLE);
        tv_date.setVisibility(View.VISIBLE);
        iv_addcity.setVisibility(View.VISIBLE);
        tv_weather.setVisibility(View.VISIBLE);
        tv_country.setVisibility(View.VISIBLE);
        tv_time.setVisibility(View.VISIBLE);
        tv_sport.setVisibility(View.VISIBLE);
        tv_flu.setVisibility(View.VISIBLE);
    }

    public void upDateTime() {   //更新时间
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000 * 60);
    }
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            mhandler.sendEmptyMessage(1);
        }
    };
    private Handler mhandler = new Handler(){
        public void handleMessage(Message msg) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm");
            String date = sDateFormat.format(new java.util.Date());
            tv_time.setText(date);
        }
    };

    public void sendMessage (final String Address){   //开启子线程加载网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                String response = httpUtils.sendHttpRequest(Address);
                Message message = new Message();
                Bundle bundle =new Bundle();
                bundle.putString("Data",response);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }).start();
    }
}
