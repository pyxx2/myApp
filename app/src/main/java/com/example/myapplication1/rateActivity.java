package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class rateActivity extends AppCompatActivity {

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
            double value2=Double.parseDouble(value);
            value2=value2/7.23;
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
            double value2=Double.parseDouble(value);
            value2=value2/7.79;
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
            double value2 = Double.parseDouble(value);
            value2 = value2 / 0.0053;
            show.setText(String.valueOf(value2));
        }
    }
    public void chuan1(View v){
        Intent scoreIntent=new Intent(this,dbScoreActivity.class);
        EditText input= findViewById(R.id.input);
        String value=input.getText().toString();
        scoreIntent.putExtra("name1",value);
        startActivity(scoreIntent);
    }
    public void chuan2(View v){
        Intent scoreIntent=new Intent(this,dbScoreActivity.class);
        EditText input= findViewById(R.id.input);
        String value=input.getText().toString();
        scoreIntent.putExtra("name2",value);
        startActivity(scoreIntent);
    }
    public void chuan3(View v){
        Intent scoreIntent=new Intent(this,dbScoreActivity.class);
        EditText input= findViewById(R.id.input);
        String value=input.getText().toString();
        scoreIntent.putExtra("name3",value);
        startActivity(scoreIntent);
    }

}