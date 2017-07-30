package com.example.administrator.weatherapplication3.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.weatherapplication3.R;
import com.example.administrator.weatherapplication3.activity.WholeApplication;
import com.example.administrator.weatherapplication3.interfacetext.HttpUtilResult;

/**
 * Created by Administrator on 2017/7/15.
 */
/**
 *
 * 通过 volley 进行网络请求
 */

public class HttpUtils {
    public void sendImageHttpRequest(String Address, ImageView imageView){
        ImageLoader imageLoader = new ImageLoader(WholeApplication.getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
            }
        });
        ImageLoader.ImageListener listener=ImageLoader.getImageListener(imageView, R.drawable.back,R.drawable.addcity);
        imageLoader.get(Address,listener);
    }
    public void sendHttpRequest(final String Address, final HttpUtilResult httpUtilReault) {
        StringRequest stringRequest = new StringRequest(Address, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                httpUtilReault.successReturn(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                httpUtilReault.errorRetuen(volleyError);
            }
        });
        WholeApplication.getRequestQueue().add(stringRequest);
    }
}
    /*public  String sendHttpRequest(final String Address) {   // 网络请求
        StringBuilder response = new StringBuilder();

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(Address);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return response.toString();

    }
}*/