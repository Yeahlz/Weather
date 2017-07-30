package com.example.administrator.weatherapplication3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/18.
 */
/**
 * 数据库帮助类
 *
 */
public class LocationHelper extends SQLiteOpenHelper {
    public static final String CREATE_PROVINCE = "create table Province("
            +"id integer primary key autoincrement,"
            +"provinceId text,"
            +"provinceName text)";
    public static final String CREATE_CITY = "create table City("
            +"id integer primary key autoincrement,"
            +"provinceId text,"
            +"cityId text,"
            +"cityName text)";
    public static final String CREATE_COUNTRY = "create table Country("
            +"id integer primary key autoincrement,"
            +"cityId text,"
            +"countryName text,"
            +"countryId  text,"
            +"weatherId text)";
    private Context mContext;
    public LocationHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}


