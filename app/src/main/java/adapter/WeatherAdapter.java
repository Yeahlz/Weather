package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.weatherapplication3.R;

import java.util.ArrayList;
import java.util.List;

import databean.CountryData;

/**
 * Created by Administrator on 2017/7/15.
 */

public class WeatherAdapter extends BaseAdapter {   //作为显示天气的适配器
    Context context ;

    List<CountryData> weatherList = new ArrayList<>();

    public WeatherAdapter(Context context ,List<CountryData>list){
        this.context = context;
        this.weatherList = list;
    }
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.weather_list, null);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_weather =(TextView) convertView.findViewById(R.id.tv_weather);
            viewHolder.tv_hightemperature =(TextView)convertView.findViewById(R.id.tv_hightemperature);
            viewHolder.tv_lowtemperature=(TextView)convertView.findViewById(R.id.tv_lowtemperature);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_date.setText(weatherList.get(position).getDate());
        viewHolder.tv_weather.setText(weatherList.get(position).getWeather());
        viewHolder.tv_hightemperature.setText(weatherList.get(position).getHighTemperature()+"℃");
        viewHolder.tv_lowtemperature.setText(weatherList.get(position).getLowTemperature()+"℃");
        return convertView;

    }
    public static class ViewHolder{
        TextView tv_date;
        TextView tv_weather;
        TextView tv_hightemperature;
        TextView tv_lowtemperature;
    }
}
