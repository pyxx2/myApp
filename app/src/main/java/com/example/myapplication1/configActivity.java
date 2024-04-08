package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

public class configActivity extends AppCompatActivity {

    EditText dollarEdit,eruoEdit,wonEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        //接收传入的数据
        Intent intent=getIntent();
        float dollar1=intent.getFloatExtra("dollar_key",0f);
        float eruo1=intent.getFloatExtra("eruo_key",0f);
        float won1=intent.getFloatExtra("won_key",0f);

        //显示到控件中
        dollarEdit=findViewById(R.id.dollar_rate);
        eruoEdit=findViewById(R.id.eruo_rate);
        wonEdit=findViewById(R.id.won_rate);

        dollarEdit.setText(String.valueOf(dollar1));
        eruoEdit.setText(String.valueOf(eruo1));
        wonEdit.setText(String.valueOf(won1));
    }
    public void save(View v){
        //保存数据，返回调用页面
//        Intent config=new Intent(this,rateActivity.class);
        float new_dollar=Float.parseFloat(dollarEdit.getText().toString());
        float new_eruo=Float.parseFloat(eruoEdit.getText().toString());
        float new_won=Float.parseFloat(wonEdit.getText().toString());

        //带回数据到调用页面rateActivity
        Intent retintent=getIntent();
        Bundle bdl=new Bundle();
        bdl.putFloat("dollar_key2",new_dollar);
        bdl.putFloat("eruo_key2",new_eruo);
        bdl.putFloat("won_key2",new_won);

        retintent.putExtras(bdl);
        setResult(2,retintent);

        finish();
    }
}