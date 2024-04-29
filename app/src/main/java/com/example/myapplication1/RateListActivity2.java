package com.example.myapplication1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateListActivity2 extends ListActivity implements Runnable{

    Handler handler;
    String text;//存储提取到的信息
    String[] ratemsg;//存储每一天汇率信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new Handler(Looper.myLooper()){
            public void handleMessage(@NonNull Message msg){
                Log.i(TAG,"handleMessage:收到消息"+msg.what);
                if(msg.what==6){
                    Log.i(TAG, "handleMessage:!!!!!!"+msg.obj);
                    //准备数据,不需要准备页面布局
                    List<String> list1=new ArrayList<String>();

                    for(int id=1;id<(int)ratemsg.length;id++){
                        list1.add(ratemsg[id]);
                    }//RateListActivity2.this,android.R.layout.simple_list_item_1,list1
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity2.this,android.R.layout.simple_list_item_1,list1);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        Thread t =new Thread(this::run);
        t.start();
        Log.i(TAG,"on create:t.start()........");


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

            for (Element ulElement : tables) {
                Elements liElements = ulElement.select("li"); // 在 ul 元素内部选取 li 元素
                for (Element liElement : liElements) {
                    if(id!=0) break;
                    text = liElement.text(); // 获取 li 元素的文本内容
//                    Log.i(TAG, "run: "+text);
                    id=1;
                }
            }
            ratemsg=text.split("汇率 ");//用正则表达式提取出有用信息
            for(String s:ratemsg){
                Log.i(TAG, "run: "+s);
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //发送数据回主线程
        Message msg=handler.obtainMessage(6);
        msg.obj="helloword";
        handler.sendMessage(msg);
        Log.i(TAG,"run:msg已发送");
    }
}