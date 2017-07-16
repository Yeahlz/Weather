package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.weatherapplication3.R;

import java.util.ArrayList;
import java.util.List;

import databean.LocationData;

/**
 * Created by Administrator on 2017/7/15.
 */

public class LocationAdapter extends BaseAdapter {  // 作为显示省市县的适配器
    Context context ;
    List<LocationData> locationList = new ArrayList<>();

    public LocationAdapter(Context context, List<LocationData>list ){
        this.context = context;
        this.locationList =list;
    }
    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.city_list,null);
            viewHolder.tv_location =(TextView)convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder) convertView.getTag();
        }
        viewHolder.tv_location.setText(locationList.get(position).getName());
        return convertView;
    }

         public static class ViewHolder{
            TextView tv_location;
         }
}
