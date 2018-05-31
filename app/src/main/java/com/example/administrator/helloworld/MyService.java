package com.example.administrator.helloworld;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }


    private DownloadBinder mBinder = new DownloadBinder();

    //服务内部类
    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService", "startDownload executed");
            Toast.makeText(MyService.this, "startDownload executed", Toast.LENGTH_SHORT).show();
        }
        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            Toast.makeText(MyService.this, "getProgress executed", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //返回内部类对象
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "MyService onCreate!", Toast.LENGTH_SHORT).show();


        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker("这是一个通知222")
                .setContentTitle("这是TITLE2222")
                .setContentText("这是2222通知的内容这是通知的内容这是通知的内容这是通知的内容这是通知的内容这是通知的内容")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.pineapple_pic);


        Notification notification = builder.getNotification();
        //notification.defaults = Notification.DEFAULT_ALL;

        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "MyService start !", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "MyService onDestory !", Toast.LENGTH_SHORT).show();
    }
}
