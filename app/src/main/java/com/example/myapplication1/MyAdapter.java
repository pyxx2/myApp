package com.example.myapplication1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter{
    private static final String tag="MyAdapter";
    public  MyAdapter(Context context, int resource, ArrayList<HashMap<String,String>>list){
        super(context,resource,list);
    }

    public View getView(int position, View converView, ViewGroup parent){
        View itemView=converView;
        if(itemView==null){
            itemView= LayoutInflater.from(getContext()).inflate(R.layout.activity_rate_list3,parent,false);
        }
        Map<String,String> map=(Map<String,String>) getItem(position);
        TextView title=(TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail=(TextView) itemView.findViewById(R.id.itemDetail);
        title.setText("Title:"+map.get("ItemTitle"));
        detail.setText("detail:"+map.get("ItemDetail"));

        //根据汇率设置背景颜色
        Resources.Theme theme=getContext().getTheme();
        int myCustomColor=getContext().getResources().getColor(R.color.my_light_primary,theme);
        int defaultColor= ContextCompat.getColor(getContext(),R.color.white);
        itemView.setBackgroundColor(defaultColor);
        try {
            float rate=Float.parseFloat(map.get("ItemDetail"));
            float redIntensity=(float) rate/900f;
            int redColor= Color.argb((int)(255*redIntensity),255,0,0);
            itemView.setBackgroundColor(redColor);

        }catch (NumberFormatException ex){
//            Log.i("getView:"+ex);
        }
        return itemView;
    }
}
