package com.example.administrator.weatherapplication3.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.weatherapplication3.R;
import com.example.administrator.weatherapplication3.adapter.RecyclerViewDivider;
import com.example.administrator.weatherapplication3.adapter.WeatherDataAdapter;
import com.example.administrator.weatherapplication3.databean.Forecast;
import com.example.administrator.weatherapplication3.databean.Weather;
import com.example.administrator.weatherapplication3.interfacetext.HttpUtilResult;
import com.example.administrator.weatherapplication3.utils.HttpUtils;
import com.example.administrator.weatherapplication3.utils.WeatherUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/24.
 */
/**
 *
 * 天气信息碎片
 */
public class WeatherFragment extends Fragment {
    /**
     *
     * 一些控件的声明和实例
     */
    TextView mtv_flu;
    TextView mtv_sport;
    TextView mtv_carwash;
    TextView mtv_time;
    TextView mtv_country;
    TextView mtv_date;
    TextView mtv_temperature;
    TextView mtv_weather;
    Weather mweather;
    List<Forecast> mweatherDatalist = new ArrayList<>();
    WeatherDataAdapter mWeatherAdapter;
    RecyclerView mweatherRecyclerView;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    int i = 1;
    String date;
    Timer timer;
    String path;
    HttpUtils httpUtils = new HttpUtils();
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        if (timer==null){
            upDateTime();
        }else{
            nowTime();
        }
        return view;
    }
    /**
     *
     * 从 activity 中获取天气信息并解析
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            String weather = getArguments().getString("data");
            path = getArguments().getString("path");
            mweather = WeatherUtil.paresonWeather(weather);
            showWeather(mweather);
        }
    }
    /**
     *
     * 将天气信息显示到控件上
     */
    private void showWeather(final Weather weather) {
        swipeRefreshLayout.setRefreshing(false);
        mweatherDatalist.clear();
        mweatherDatalist.addAll(weather.forecastList);
        mWeatherAdapter = new WeatherDataAdapter(mweatherDatalist);
        mweatherRecyclerView.setLayoutManager(linearLayoutManager);
        mweatherRecyclerView.setAdapter(mWeatherAdapter);
        mweatherRecyclerView.addItemDecoration(new RecyclerViewDivider(WholeApplication.getContext(), LinearLayoutManager.VERTICAL));
        mtv_country.setText(weather.basic.cityName);
        mtv_date.setText(mweatherDatalist.get(0).date);
        mtv_carwash.setText("洗车指数:"+weather.suggestion.carWash.carWashSuggestion);
        mtv_sport.setText("运动建议:" + weather.suggestion.sport.sportSuggestion);
        mtv_flu.setText("舒适程度:" + weather.suggestion.comfort.lifeSuggestion);
        mtv_temperature.setText(weather.now.temperaturre + "℃");
        mtv_weather.setText(weather.now.more.nowWeather);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpUtils.sendHttpRequest(path, new HttpUtilResult() {
                    @Override
                    public void successReturn(String s) {
                        mweather = WeatherUtil.paresonWeather(s);
                        showWeather(weather);
                    }

                    @Override
                    public void errorRetuen(VolleyError volleyError) {

                    }
                });
            }
            //  mWeatherAdapter.notifyDataSetChanged();
            //  if (updateflag){
            //        swipeRefreshLayout.setRefreshing(false);
            //    }
            //    if (mflag) {
            //      mweatherService.getWeatherDatalist(mweatherDatalist);
            //        mweatherService.getWeatherObject(weather);
            //       mweatherService.upDateWeather();
        });
    }
    public void upDateTime() {
         timer = new Timer();
        timer.schedule(timerTask, 0, 1000 * 60);
    }
/**
 * 获取当时时间，通过 Timer 计时器
 * 每分钟获取一次时间
 */
    TimerTask timerTask = new TimerTask() {
         @Override
        public void run() {
            mhandler.sendEmptyMessage(1);
        }
    };
    private Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm");
             date = sDateFormat.format(new java.util.Date());
            mtv_time.setText(date);
        }
    };

    public void nowTime(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm");
        date = sDateFormat.format(new java.util.Date());
        mtv_time.setText(date);
    }

    /**
     *
     * 初始化控件
     */
    private void initView(){
        mtv_date = (TextView) view.findViewById(R.id.main_tv_date);
        mtv_temperature = (TextView) view.findViewById(R.id.main_tv_temperature);
        mtv_country = (TextView) view.findViewById(R.id.main_tv_countryname);
        mtv_weather = (TextView) view.findViewById(R.id.main_tv_weather);
        mtv_sport = (TextView) view.findViewById(R.id.main_tv_sport);
        mtv_carwash=(TextView)view.findViewById(R.id.fragment_tv_car_wash);
        mtv_flu = (TextView) view.findViewById(R.id.main_tv_flu);
        mtv_time = (TextView) view.findViewById(R.id.main_tv_time);
        mweatherRecyclerView = (RecyclerView) view.findViewById(R.id.main_rv_weather);
        linearLayoutManager = new LinearLayoutManager(WholeApplication.getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.main_srl_update);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }
}

