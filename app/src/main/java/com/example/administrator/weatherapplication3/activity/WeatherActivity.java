package com.example.administrator.weatherapplication3.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.example.administrator.weatherapplication3.R;
import com.example.administrator.weatherapplication3.interfacetext.HttpUtilResult;
import com.example.administrator.weatherapplication3.interfacetext.WeatherIdClickListener;
import com.example.administrator.weatherapplication3.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements WeatherIdClickListener {
    /**
     *   一些接口地址
     */
    private static final String WeatherURL1 = "&key=83c091f8f3de49c3a54a638285baa1a1";
    private static final String WeatherURL = "http://guolin.tech/api/weather?cityid=";
    String url = "http://guolin.tech/api/weather?cityid=CN101280101&key=83c091f8f3de49c3a54a638285baa1a1";
    /**
     *
     * 一些变量的声明和实例
     */
    HttpUtils httpUtil = new HttpUtils();
    String mweatherId;
    boolean flag;
    ViewPager mViewPager;
    List<Fragment> mfragmentList;
    MyViewpaperAdapter myViewpaperAdapter;
    private DrawerLayout drawerLayout;
    int i = 1;
    List<String> mWeatherIdList = new ArrayList<>();
    UpdateDialoFragment updateDialoFragment;
    boolean mweatherflag = false;
    /**
     *
     * 重写 FragmentPaperAdapter
     */
    public class MyViewpaperAdapter extends FragmentPagerAdapter {
        public MyViewpaperAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            mfragmentList = list;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return mfragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mfragmentList.get(position);
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
        mWeatherIdList.add("CN101280101");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        sendMessage(url);
        updateDialoFragment = new UpdateDialoFragment();
    }

    /**
     *
     * 点击侧滑出菜单
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
    /**
     *
     * 通过接口回调获得 weatherId 更新数据
     * 通过 dialofragment 显示正在加载
     */
    @Override
    public void sendWeatherId(String weatherId) {
        updateDialoFragment.show(getSupportFragmentManager(), "UpdateDialoFragment");
        this.mweatherId = weatherId;
        drawerLayout.closeDrawers();
        for (int i = 0; i < mWeatherIdList.size(); i++) {
            if (mWeatherIdList.get(i) == mweatherId) {
                updateDialoFragment.dismiss();
                mViewPager.setCurrentItem(i);
                flag = true;
                break;
            }
        }
        if (!flag) {
            mWeatherIdList.add(mweatherId);
            mweatherflag = true;
            sendMessage(WeatherURL + mweatherId + WeatherURL1);
            flag = false;
        }
    }

    /**
     * 进行网络请求并判断是否要新建 fragment
     *
     */
    public void sendMessage(final String Address) {
        httpUtil.sendHttpRequest(Address, new HttpUtilResult() {
            @Override
            public void successReturn(String s) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("weather", s);
                editor.apply();
                if (Address == url) {
                    Fragment weatherFragment = new WeatherFragment();
                    mfragmentList = new ArrayList<>();
                    mfragmentList.add(weatherFragment);
                    Bundle args = new Bundle();
                    args.putString("data", s);
                    args.putString("path", url);
                    weatherFragment.setArguments(args);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    myViewpaperAdapter = new MyViewpaperAdapter(fragmentManager, mfragmentList);
                    mViewPager.setAdapter(myViewpaperAdapter);
                    mViewPager.setCurrentItem(0);
                }
                if (mweatherflag) {
                    Fragment fragment = new WeatherFragment();
                    i = i + 1;
                    Bundle args = new Bundle();
                    args.putString("data", s);
                    args.putString("path", WeatherURL + mweatherId + WeatherURL1);
                    fragment.setArguments(args);
                    mfragmentList.add(fragment);
                    myViewpaperAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(i);
                    mweatherflag = false;
                    updateDialoFragment.dismiss();
                }
            }

            @Override
            public void errorRetuen(VolleyError volleyError) {

            }

        });
    }
}










































