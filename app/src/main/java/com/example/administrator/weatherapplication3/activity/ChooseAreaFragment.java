package com.example.administrator.weatherapplication3.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.administrator.weatherapplication3.R;
import com.example.administrator.weatherapplication3.adapter.AreaAdapter;
import com.example.administrator.weatherapplication3.adapter.RecyclerViewDivider;
import com.example.administrator.weatherapplication3.databean.AreaCountryData;
import com.example.administrator.weatherapplication3.databean.CityData;
import com.example.administrator.weatherapplication3.databean.ProvinceData;
import com.example.administrator.weatherapplication3.db.LocationHelper;
import com.example.administrator.weatherapplication3.interfacetext.HttpUtilResult;
import com.example.administrator.weatherapplication3.interfacetext.ItemOnclickListener;
import com.example.administrator.weatherapplication3.interfacetext.WeatherIdClickListener;
import com.example.administrator.weatherapplication3.utils.GsonUtil;
import com.example.administrator.weatherapplication3.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/25.
 */

/**
 * 城市选择碎片
 */
public class ChooseAreaFragment extends Fragment implements ItemOnclickListener{
    /**
     *
     * 一些控件的声明和实例
     */
    WeatherIdClickListener weatherIdClickListener;
    String mprovinceId;
    String mcityId;
    String mweatherId;
    List<String> mlocationDataList ;
    List<CityData> mcityDataList =new ArrayList<>();
    List<AreaCountryData> mcountryDataList=new ArrayList<>();
    List<ProvinceData> mprovinceDataList=new ArrayList<>();
    GsonUtil gsonUtil =new GsonUtil();
    HttpUtils httpUtil =new HttpUtils();
    public static LocationHelper shelper ;
    public static SQLiteDatabase sdb;
    RecyclerView mlocationRecyclerView;
    AreaAdapter mAreaAdapter;
    View view;
    /**
     *
     *   一些地址常量
     */
    public static final String GETDATA = "GETDATA ";
    private static final String PROVINCE = "http://guolin.tech/api/china";

    /**
     *
     *  根据不同返回省市县
     */
    private final int mcityFlag= 2;
    private final int mcountryFlag = 3;
    private int mlocationFlag = 2;
    /**
     *
     * 获取 Activity 引用
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        weatherIdClickListener=(WeatherIdClickListener)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_choose_area,container,false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        queryProvinces();
        mAreaAdapter.setOnItemClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }
    /**
     *
     * 初始化控件
     */
    private void initView(){
        shelper = new LocationHelper(WholeApplication.getContext(), "Location", null, 1);
        sdb= shelper.getReadableDatabase();
        mlocationDataList =new ArrayList<>();
        mlocationDataList.add("A");
        mlocationRecyclerView = (RecyclerView)view.findViewById(R.id.location_rv_showlocation);
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(WholeApplication.getContext());
        mAreaAdapter = new AreaAdapter(mlocationDataList);
        mlocationRecyclerView.setLayoutManager(wrapContentLinearLayoutManager);
        mlocationRecyclerView.setAdapter(mAreaAdapter);
        mlocationRecyclerView.addItemDecoration(new RecyclerViewDivider(WholeApplication.getContext(), LinearLayoutManager.VERTICAL));
    }
    /**
     * 重写 LinearLayoutManger 来 catch 处 recyclerView
     * 中出现的 IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter 异常
     *
     */
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 城市列表 recyclerView 点击事件
     *
     */
    @Override
    public void setOnItemClick(View view, int position) {
        switch (mlocationFlag) {
            case mcityFlag:
                mprovinceId = mprovinceDataList.get(position).getProvinceId();
                queryCity();
                mlocationFlag = mcountryFlag;
                break;
            case mcountryFlag:
                mcityId = mcityDataList.get(position).getCityId();
                queryCountry();
                mlocationFlag = mcountryFlag + 1;
                break;
            case mcountryFlag + 1:
                mweatherId = mcountryDataList.get(position).getWeatherId();
                weatherIdClickListener.sendWeatherId(mweatherId);
                queryProvinces();
                mlocationFlag=2;
                break;
            default:
                break;
        }
    }
    /**
     *
     * 查询所有省份，先从数据库里面查找是否有所有省份的数据
     * 假如没有再请求网络获得数据后再从数据库里面查找数据
     *
     */
    private void queryProvinces(){
        Cursor cursor =sdb.query("Province",null,null,null,null,null,null);
        mlocationDataList.clear();
        mprovinceDataList.clear();
        if (cursor.moveToFirst()) {
            do {
                ProvinceData provinceData = new ProvinceData();
                String id = cursor.getString(cursor.getColumnIndex("provinceId"));
                String name = cursor.getString(cursor.getColumnIndex("provinceName"));
                provinceData.setProvinceName(name);
                provinceData.setProvinceId(id);
                mprovinceDataList.add(provinceData);
                mlocationDataList.add(name);
            }while(cursor.moveToNext());
        }
        if (mlocationDataList.size()>0){
            mAreaAdapter.notifyDataSetChanged();
        }else{
            queryProvinceData(PROVINCE);
        }
    }
    /**
     *
     *通过网络获取数据并存入数据库
     *
     */
    private void queryProvinceData(String Address) {

        httpUtil.sendHttpRequest(Address, new HttpUtilResult() {
            @Override
            public void successReturn(String s) {
                gsonUtil.parseProvinceData(s);
                queryProvinces();
            }

            @Override
            public void errorRetuen(VolleyError volleyError) {

            }
        });
    }
    /**
     *
     * 查询带有特定省 id 的城市，先从数据库里面查找是否有所有该省 id 的城市数据
     * 假如没有再请求网络获得数据后再从数据库里面查找数据
     *
     */
    private void queryCity(){
        Cursor cursor =sdb.query("City",null,"provinceId = ?",new String[]{String.valueOf(mprovinceId)},null,null,null);
        mcityDataList.clear();
        mlocationDataList.clear();
        if (cursor.moveToFirst()){
            do {
                CityData cityData = new CityData();
                String id = cursor.getString(cursor.getColumnIndex("cityId"));
                String name = cursor.getString(cursor.getColumnIndex("cityName"));
                cityData.setCityName(name);
                cityData.setCityId(id);
                mcityDataList.add(cityData);
                mlocationDataList.add(name);
            }while (cursor.moveToNext());
        }
        if (mlocationDataList.size()>0){
            mAreaAdapter.notifyDataSetChanged();
        }else{
            queryCityData(PROVINCE+"/"+mprovinceId);
        }
    }
    /**
     *
     *通过网络获取数据并存入数据库
     *
     */
    private void queryCityData(String Address ) {
        httpUtil.sendHttpRequest(Address, new HttpUtilResult() {
            @Override
            public void successReturn(String s) {
                try {
                    gsonUtil.parseCityData(s,mprovinceId);
                    queryCity();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void errorRetuen(VolleyError volleyError) {

            }
        });
    }
    /**
     *
     * 查询带有特定市 id 的县城，先从数据库里面查找是否有所有该市 id 的县城数据
     * 假如没有再请求网络获得数据后再从数据库里面查找数据
     *
     */
    private void queryCountryData(String Address ){
        httpUtil.sendHttpRequest(Address, new HttpUtilResult() {
            @Override
            public void successReturn(String s) {
                gsonUtil.parseCountryData(s,mcityId);
                queryCountry();
            }

            @Override
            public void errorRetuen(VolleyError volleyError) {

            }
        });
    }
    /**
     *
     *通过网络获取数据并存入数据库
     *
     */
    private void queryCountry() {
        Log.d("AAA","县城");
        try {
            Cursor cursor = sdb.query("Country", null, "cityId = ?", new String[]{String.valueOf(mcityId)}, null, null, null);
            mcountryDataList.clear();
            mlocationDataList.clear();
            if (cursor.moveToFirst()) {
                do {
                    AreaCountryData countryData = new AreaCountryData();
                    String id = cursor.getString(cursor.getColumnIndex("countryId"));
                    String name = cursor.getString(cursor.getColumnIndex("countryName"));
                    String weatherId = cursor.getString(cursor.getColumnIndex("weatherId"));
                    countryData.setCountryName(name);
                    countryData.setCountryName(id);
                    countryData.setWeatherId(weatherId);
                    mcountryDataList.add(countryData);
                    mlocationDataList.add(name);
                } while (cursor.moveToNext());
            }
            if (mlocationDataList.size() > 0) {
                mAreaAdapter.notifyDataSetChanged();
            } else {
                queryCountryData(PROVINCE +"/"+ mprovinceId +"/"+ mcityId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
