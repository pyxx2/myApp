package com.example.myapplication1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateListActivity3 extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    Handler handler;
    String text;//存储提取到的信息
    String[] ratemsg;
    ArrayList<HashMap<String,String>>listItems=new ArrayList<HashMap<String,String>>();
    MyAdapter myAdapter;

    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","lastRateDateStr=" + logDate);

        handler=new Handler(Looper.myLooper()){
            public void handleMessage(@NonNull Message msg){
                Log.i(TAG,"handleMessage:收到消息"+msg.what);
                if(msg.what==6){
                    Toast.makeText(RateListActivity3.this,"数据已更新",Toast.LENGTH_SHORT).show();
                    listItems=(ArrayList<HashMap<String, String>>) msg.obj;
                    //准备数据,不需要准备页面布局

                     myAdapter=new MyAdapter(RateListActivity3.this,R.layout.activity_rate_list3,listItems);
                    setListAdapter(myAdapter);
                    Toast.makeText(RateListActivity3.this,"insert over",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

        Thread t =new Thread(this::run);
        t.start();
        Log.i(TAG,"on create:t.start()........");
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
    }
    public void run(){
        Log.i(TAG,"run............");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            throw  new RuntimeException(e);
        }
        int id=0;
        try {
            Document doc= Jsoup.connect("https://www.huilvzaixian.com/").get();
            Elements tables=doc.getElementsByTag("ul");
            String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
            for (Element ulElement : tables) {
                Elements liElements = ulElement.select("li"); // 在 ul 元素内部选取 li 元素
                for (Element liElement : liElements) {
                    if(id!=0) break;
                    text = liElement.text(); // 获取 li 元素的文本内容
///                    Log.i(TAG, "run: "+text);
                    id=1;
                }
            }
            ratemsg=text.split("\\s");//用正则表达式提取出有用信息


            //判断当前日期和已经存在的日期是否相等
            if(curDateStr.equals(logDate)){
                //如果相等，则不从网络中获取数据
                Log.i("run","日期相等，不进行更新");
            }else{
                Log.i("run","日期不相等，更新");
                logDate=curDateStr;
                Log.i(TAG, "run: date"+logDate);
                //创建数据，加载到数据库中
                RateManager rateManager=new RateManager(RateListActivity3.this);
                RateItem item=new RateItem();

                for(int i=0;i+7<ratemsg.length;i+=3){
                    String name=ratemsg[i+1];
                    String rate=ratemsg[i+2];
                    Log.i(TAG, "run: "+name+"==>"+rate);
                    HashMap<String,String>map=new HashMap<>();
                    map.put("ItemTitle",name);
                    map.put("ItemDetail",rate);
                    //放入数据库中
                    item.setCname(name);
                    item.setCval(rate);
                    rateManager.add(item);
                    Log.i(TAG, "run: "+map);
                    listItems.add(map);
                }
            }
//            for (Element ulElement : tables) {
//                Elements liElements = ulElement.select("li"); // 在 ul 元素内部选取 li 元素
//                for (Element liElement : liElements) {
//                    if(id!=0) break;
//                    text = liElement.text(); // 获取 li 元素的文本内容
/////                    Log.i(TAG, "run: "+text);
//                    id=1;
//                }
//            }
//            ratemsg=text.split("\\s");//用正则表达式提取出有用信息
//            RateManager rateManager=new RateManager(RateListActivity3.this);
//                    RateItem item=new RateItem();
//            for(int i=0;i+7<ratemsg.length;i+=3){
//                String name=ratemsg[i+1];
//                String rate=ratemsg[i+2];
//                Log.i(TAG, "run: "+name+"==>"+rate);
//                HashMap<String,String>map=new HashMap<>();
/////                map3.put("ItemTitle",name);
/////                map3.put("ItemDetail",rate);
//                map.put("ItemTitle",name);
//                map.put("ItemDetail",rate);
//                item.setCname(name);
//                item.setCval(rate);
//                rateManager.add(item);
//                Log.i(TAG, "run: "+map);
//                listItems.add(map);
//            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //发送数据回主线程
        Message msg=handler.obtainMessage(6);
        msg.obj=listItems;
        handler.sendMessage(msg);
        Log.i(TAG,"run:msg已发送");
    }
    public void onItemClick(AdapterView<?> adapterView, View view,int position,long id){
        HashMap<String,String>map2=(HashMap<String, String>) getListView().getItemAtPosition(position);
        String name=map2.get("ItemTitle");
        String rate=map2.get("ItemDetail");
        Log.i(TAG, "onItemClick: name="+name+"==>"+rate);
        myAdapter.remove(map2);
       // myAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按当前行");
        return true;
    }
}