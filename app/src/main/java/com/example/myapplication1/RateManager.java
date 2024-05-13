package com.example.myapplication1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RateManager {
    private  DBHelper dbHelper;
    private String TBNAME;
    public RateManager(Context context){
        dbHelper=new DBHelper(context);
        TBNAME =DBHelper.TB_NAME;
    }

    public  void add(RateItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("curname",item.getCname());
        values.put("currate",item.getCval());
        db.insert(TBNAME,null,values);
        //db.close();
    }
}
