package com.example.myapplication1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class rateActivity extends AppCompatActivity {

    float dollarRate=7.23f;
    float euroRate=7.83f;
    float wonRate=0.0053f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
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
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}