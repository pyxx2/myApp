package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class dbScoreActivity extends AppCompatActivity {
    Integer suma=0;
    Integer sumb=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_score);
    }
    public void click1(View v){

        TextView show =findViewById(R.id.scoreA);
        if(v.getId()==R.id.bnt3a){
            suma+=3;
        }else if(v.getId()==R.id.bnt2a){
            suma+=2;
        }else{
            suma+=1;
        }
        show.setText(String.valueOf(suma));
    }
    public void click2(View v){

        TextView show =findViewById(R.id.scoreB);
        if(v.getId()==R.id.bnt3b){
            sumb+=3;
        }else if(v.getId()==R.id.bnt2b){
            sumb+=2;
        }else{
            sumb+=1;
        }
        show.setText(String.valueOf(sumb));
    }

    public void clickReset(View v){
        suma=0;
        sumb=0;
        TextView show1=findViewById(R.id.scoreA);
        TextView show2=findViewById(R.id.scoreB);
        show1.setText(String.valueOf(suma));
        show2.setText(String.valueOf(sumb));
    }
}