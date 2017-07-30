package com.example.administrator.weatherapplication3.activity;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/24.
 */

/**
 *
 *全局类获取 context 和 requestQueue
 */
public class WholeApplication extends Application {
    private static RequestQueue mRequestQueue;
    private static Context context;
    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context =getApplicationContext();
    }


    public static RequestQueue getRequestQueue() {

            mRequestQueue = Volley.newRequestQueue(context);

        return mRequestQueue;
    }
}
