package com.example.administrator.weatherapplication3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.weatherapplication3.R;
import com.example.administrator.weatherapplication3.databean.Forecast;

import java.util.List;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/17.
 */
/**
 *
 * 自定义适配器，天气信息跟数据源结合
 */
public class WeatherDataAdapter extends  RecyclerView.Adapter<WeatherDataAdapter.ViewHolder>{

    List<Forecast> mweatherList ;

    public WeatherDataAdapter(List<Forecast>list){
        mweatherList =list;
    }

     static class ViewHolder extends RecyclerView.ViewHolder{
        View mweatherView;
        TextView mtv_date;
        TextView mtv_weather;
        TextView mtv_hightemperature;
        TextView mtv_lowtemperature;

        public ViewHolder(final View itemView) {
            super(itemView);
            mweatherView = itemView;
            mtv_date = (TextView) mweatherView.findViewById(R.id.weather_list_tv_date);
            mtv_weather =(TextView) mweatherView.findViewById(R.id.weather_list_tv_weather);
            mtv_hightemperature =(TextView)mweatherView.findViewById(R.id.weather_list_tv_hightemperature);
            mtv_lowtemperature=(TextView)mweatherView.findViewById(R.id.weather_list_tv_lowtemperature);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_weather_list,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Forecast forecast = mweatherList.get(position);
        holder.mtv_date.setText(forecast.date);
        holder.mtv_weather.setText(forecast.more.forcastWeather);
        holder.mtv_hightemperature.setText(forecast.temperature.max+"℃");
        holder.mtv_lowtemperature.setText(forecast.temperature.min+"℃");
    }

    @Override
    public int getItemCount() {
        return  mweatherList.size();
    }

}
