package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Integer sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void myClick(View v){
        sum=0;
        TextView show=findViewById(R.id.textView4);
        show.setText(String.valueOf(sum));
    }
    public  void plus1(View v){
        sum+=1;
        TextView show=findViewById(R.id.textView4);
        show.setText(String.valueOf(sum));
        sum--;
    }
    public void plus2(View v){
        sum+=2;
        TextView show=findViewById(R.id.textView4);
        show.setText(String.valueOf(sum));
    }
    public  void plus3(View v){
        sum+=3;
        TextView show=findViewById(R.id.textView4);
        show.setText(String.valueOf(sum));
    }
}