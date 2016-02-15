package com.aacer.gifview.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aacer.gifview.R;

/**
 * Created by acer on 2016/2/15.
 */
public class DLInfoFrag extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        加载布局，指定父布局，同时设置是否将布局关联到父布局
         view = inflater.inflate(R.layout.mian_fragment, container,false);
        TextView textView = (TextView) view.findViewById(R.id.tv_fragemet);
        String text = getArguments().getString("text");
        textView.setText(text);
        return view;
    }
}
