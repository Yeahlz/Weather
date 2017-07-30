package com.example.administrator.weatherapplication3.interfacetext;

import com.android.volley.VolleyError;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/18.
 */


/**
 * 网络接口
 */
public interface HttpUtilResult {

    void successReturn  (String s);
    void errorRetuen (VolleyError volleyError);

}
