package com.example.administrator.weatherapplication3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.administrator.weatherapplication3.R;

/**
 * Created by ${Learning_Yeachlz} on 2017/7/25.
 */
/**
 * dialofragmemt 应用
 */
public class UpdateDialoFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_warm_update,container,false);
        return view;
    }
}
