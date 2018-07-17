package com.example.rupali.thalassaemiaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotiOpenHelper extends SQLiteOpenHelper {
    private static NotiOpenHelper notiOpenHelper;
    public static NotiOpenHelper getInstance(Context context){
        if(notiOpenHelper==null){
            notiOpenHelper=new NotiOpenHelper(context.getApplicationContext());
        }
        return notiOpenHelper;
    }
    public NotiOpenHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String notiSql="CREATE TABLE "+Constants.NotiTAble.TABLE_NAME+" ( "+
                Constants.NotiTAble.NOTI_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Constants.NotiTAble.TITLE+" TEXT , "+Constants.NotiTAble.IMAGE_URL+" TEXT , "+Constants.NotiTAble.MESSAGE+
                " TEXT )";
        sqLiteDatabase.execSQL(notiSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
