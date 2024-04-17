package com.example.myapplication1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class rateActivity extends AppCompatActivity implements Runnable{

    float dollarRate=7.23f;
    float euroRate=7.83f;
    float wonRate=0.0053f;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        handler=new Handler(Looper.myLooper()){
            public void handleMessage(@NonNull Message msg){
                Log.i(TAG,"handleMessage:收到消息"+msg.what);
                if(msg.what==6){
                    Log.i(TAG,"handleMessage"+msg.obj);
                    Toast.makeText(rateActivity.this,"收到"+msg.obj,Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

        Thread t= new Thread(this);
        t.start();
        Log.i(TAG,"on create:t.start()........");
    }
    public void myClick(View v){
        EditText input= findViewById(R.id.input);
        TextView show =findViewById(R.id.show);
        String value=input.getText().toString();
        if(value.length()==0){              //提示
            Toast.makeText(this, "请输入金额后再计算",Toast.LENGTH_SHORT).show();
        }else{
            float value2=Float.parseFloat(value);
            value2=value2/dollarRate;
            show.setText(String.valueOf(value2));
        }


    }
    public void myClick2(View v){
        EditText input= findViewById(R.id.input);
        TextView show =findViewById(R.id.show);
        String value=input.getText().toString();
        if(value.length()==0){
            Toast.makeText(this, "请输入金额后再计算",Toast.LENGTH_SHORT).show();
        }else{
            float value2=Float.parseFloat(value);
            value2=value2/euroRate;
            show.setText(String.valueOf(value2));
        }


    }
    public void myClick3(View v){
        EditText input= findViewById(R.id.input);
        TextView show =findViewById(R.id.show);
        String value=input.getText().toString();
        if(value.length()==0){
            Toast.makeText(this, "请输入金额后再计算",Toast.LENGTH_SHORT).show();
        }else {
            float value2 = Float.parseFloat(value);
            value2 = value2 / wonRate;
            show.setText(String.valueOf(value2));
        }
    }

    public  void openconfig(View btn){
        Intent config=new Intent(this,configActivity.class);
        config.putExtra("dollar_key",dollarRate);
        config.putExtra("eruo_key",euroRate);
        config.putExtra("won_key",wonRate);
        startActivityForResult(config,6);
    }

    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        if(requestCode==6&&resultCode==2){
            Bundle bdl2=data.getExtras();

            dollarRate=bdl2.getFloat("dollar_key2");
            euroRate=bdl2.getFloat("eruo_key2");
            wonRate=bdl2.getFloat("won_key2");
        };
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void run(){
        Log.i(TAG,"run............");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            throw  new RuntimeException(e);
        }

        try {
            Document doc= Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Elements tables=doc.getElementsByTag("table");
            Element table=tables.get(1);
            Elements trs=table.getElementsByTag("tr");
            for (Element tr:trs) {
                Elements tds=tr.getElementsByTag("td");
                if(tds.size()>6){
                    String rname=tds.get(0).text();
                    String rateStr=tds.get(5).text();
                    Log.i(TAG, "run:  "+rname+"==>"+rateStr);
                    if(rname.equals("欧元")){
                        Log.i(TAG, "run: 欧元汇率="+rateStr);
                    }
                }
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //发送数据回主线程
        Message msg=handler.obtainMessage(6);
        msg.obj=":helloworld";
        handler.sendMessage(msg);
        Log.i(TAG,"run:msg已发送");
    }

    private String inputStream2String(InputStream inputStream)throws IOException{
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuffer out =new StringBuffer();
        Reader in = new InputStreamReader(inputStream,"utf-8");
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return  out.toString();
    }
}