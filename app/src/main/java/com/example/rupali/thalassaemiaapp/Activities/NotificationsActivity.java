package com.example.rupali.thalassaemiaapp.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.rupali.thalassaemiaapp.Adapters.NotificationAdapter;
import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.JavaClass.NotiOpenHelper;
import com.example.rupali.thalassaemiaapp.JavaClass.NotificationContent;
import com.example.rupali.thalassaemiaapp.R;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {
    String imageUri="";
    String title="";
    String message="";
    ArrayList<NotificationContent> notificationContentArrayList;
    NotificationAdapter notificationAdapter;
    NotiOpenHelper notiOpenHelper;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    TextView toolbarTitle;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.noti_act_toolbar_text);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Notifications");
        notificationContentArrayList=new ArrayList<>();
        notificationAdapter=new NotificationAdapter(this, notificationContentArrayList, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView=findViewById(R.id.noti_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(notificationAdapter);
        notiOpenHelper=NotiOpenHelper.getInstance(this);
        fetchDataFromDatabase();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null&&!bundle.isEmpty()) {
            if (bundle.containsKey(Constants.NotifiactionActivityConstantts.IMAGE_URI)) {
                imageUri = bundle.getString(Constants.NotifiactionActivityConstantts.IMAGE_URI);
                Log.d("MyNotification",imageUri);
            }
            if (bundle.containsKey(Constants.NotifiactionActivityConstantts.TITLE)) {
                title = bundle.getString(Constants.NotifiactionActivityConstantts.TITLE);
                Log.d("MyNotification",title);
            }
            if (bundle.containsKey(Constants.NotifiactionActivityConstantts.MESSAGE)) {
                message = bundle.getString(Constants.NotifiactionActivityConstantts.MESSAGE);
                Log.d("MyNotification",message);
            }
            if(!message.isEmpty()&&!title.isEmpty()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Constants.NotiTAble.MESSAGE, message);
                contentValues.put(Constants.NotiTAble.TITLE, title);
                contentValues.put(Constants.NotiTAble.IMAGE_URL, imageUri);
                database = notiOpenHelper.getWritableDatabase();
                long id = database.insert(Constants.NotiTAble.TABLE_NAME, null, contentValues);
                count++;
                if(count>10){
                    Cursor cursor=database.query(Constants.NotiTAble.TABLE_NAME,null,null,null,null,null,Constants.NotiTAble.NOTI_ID+" ASC");
                    if(cursor.moveToNext()) {
                        String[] selectionArgs = {cursor.getInt(cursor.getColumnIndex(Constants.NotiTAble.NOTI_ID)) + ""};
                        database.delete(Constants.NotiTAble.TABLE_NAME,Constants.NotiTAble.NOTI_ID+" =? ",selectionArgs);
                    }
                }
                //NotificationContent notificationContent = new NotificationContent(id, imageUri, title, message);
               // notificationContentArrayList.add(notificationContent);
                //notificationAdapter.notifyDataSetChanged();
                fetchDataFromDatabase();
            }

        }

    }
    public void fetchDataFromDatabase() {
        count=0;
        SQLiteDatabase database=notiOpenHelper.getReadableDatabase();
        notificationContentArrayList.clear();
        Cursor cursor=database.query(Constants.NotiTAble.TABLE_NAME,null,null,null,null,null,Constants.NotiTAble.NOTI_ID+" DESC");
        while (cursor.moveToNext()){
            count++;
            String title=cursor.getString(cursor.getColumnIndex(Constants.NotiTAble.TITLE));
            long id=cursor.getInt(cursor.getColumnIndex(Constants.NotiTAble.NOTI_ID));
            String message=cursor.getString(cursor.getColumnIndex(Constants.NotiTAble.MESSAGE));
            String imageUrl=cursor.getString(cursor.getColumnIndex(Constants.NotiTAble.IMAGE_URL));
            NotificationContent notificationContent=new NotificationContent(id,imageUrl,title,message);
            notificationContentArrayList.add(notificationContent);
        }
        notificationAdapter.notifyDataSetChanged();
    }

}
