package com.example.administrator.helloworld;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/4/11.
 */
public class TitleLayout extends LinearLayout {


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.title,this);

    }
}
