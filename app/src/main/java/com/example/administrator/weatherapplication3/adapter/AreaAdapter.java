package com.example.administrator.weatherapplication3.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.weatherapplication3.R;
import com.example.administrator.weatherapplication3.interfacetext.ItemOnclickListener;

import java.util.List;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/17.
 */

/**
 *
 *  自定义适配器，跟位置信息结合
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>implements ItemOnclickListener {
    ItemOnclickListener mLocationItemOnclickListener ;
    private List<String> mLocationDataList ;

    public AreaAdapter( List<String>list){
        mLocationDataList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mtv_location;
        public ViewHolder(final View itemView) {
            super(itemView);
            mtv_location = (TextView)itemView.findViewById(R.id.location_tv_show_location);

            }
        }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_city_list,parent,false);
        final AreaAdapter.ViewHolder viewHolder = new AreaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AreaAdapter.ViewHolder holder, final int position) {
        holder.mtv_location.setText(mLocationDataList.get(position));
        holder.mtv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AAA","click"+position);
                if (mLocationItemOnclickListener!=null){
                    mLocationItemOnclickListener.setOnItemClick(view,position);
                }
            }

    });
    }

    @Override
    public int getItemCount() {
        return mLocationDataList.size();
    }



    public void setOnItemClickListener(ItemOnclickListener mItemOnclickListener){
        mLocationItemOnclickListener = mItemOnclickListener;
    }
    @Override
    public void setOnItemClick(View view, int position) {

    }
}
