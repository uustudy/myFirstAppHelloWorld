package com.example.administrator.helloworld;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends BaseActivity {

    EditText editText, editText2;

    private Button changeTextBtn;
    private EditText changeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_layout);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("param1", "Hello");
                intent.putExtra("param2", "World!");
                startActivityForResult(intent, 1);
            }
        });

        Button btn22 = (Button) findViewById(R.id.btn22);
        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JsActivity.class);
                intent.putExtra("param1", "Hello");
                intent.putExtra("param2", "World!");
                startActivityForResult(intent, 11);
            }
        });


       /* editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        String text = load();
        if (editText != null) {
            editText.setText(text);
            editText.setSelection(text.length());
            Toast.makeText(this, "editText:" + editText, Toast.LENGTH_SHORT).show();
        }*/

        Button saveData = (Button) findViewById(R.id.saveData);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data222", MODE_PRIVATE).edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 28);
                editor.putBoolean("married", false);
                //editor.commit();
            }
        });


        Button saveMoreData = (Button) findViewById(R.id.saveMoreData);
        saveMoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data333", MODE_PRIVATE).edit();
                editor.putString(editText.getText().toString(), editText2.getText().toString());
                editor.commit();

                editText.setText("");
                editText2.setText("");
            }
        });

        final MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this, "Book.db", null, 4);
        Button db1 = (Button) findViewById(R.id.db1);
        db1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values); // 插入第一条数据
                values.clear();

                // 开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values); // 插入第二条数据
            }
        });

        Button dbUpdate = (Button) findViewById(R.id.dbUpdate);
        dbUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("price", 11);
                db.update("book", values, "name=?", new String[]{"The Lost Symbol"});
            }
        });


        Button sendnotice = (Button) findViewById(R.id.sendnotice);
        sendnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                /*Notification notification = new Notification(R.drawable.ic_launcher, "This is ticker text", System.currentTimeMillis());
                notification.setLatestEventInfo(this, "This is content title","This is content text", null);*/

                Notification.Builder builder = new Notification.Builder(MainActivity.this);
                builder.setTicker("这是一个通知")
                        .setContentTitle("这是TITLE")
                        .setContentText("这是通知的内容这是通知的内容这是通知的内容这是通知的内容这是通知的内容这是通知的内容")
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.pineapple_pic);


                Notification notification = builder.getNotification();
                notification.defaults = Notification.DEFAULT_ALL;

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                builder.setContentIntent(pendingIntent);

                manager.notify(101, notification);
            }
        });


        changeText = (EditText) findViewById(R.id.changeText);
        changeTextBtn = (Button) findViewById(R.id.changeTextBtn);
        changeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //changeText.setText("Nice to meet you");
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message); // 将Message对象发送出去
                    }
                }).start();

            }
        });




        final Button startService = (Button) findViewById(R.id.startService);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);
            }
        });
        Button stopService = (Button) findViewById(R.id.stopService);
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyService.class);
                stopService(intent);
            }
        });


        final Button bindService = (Button) findViewById(R.id.bindService);
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyService.class);
                bindService(intent,connection,BIND_AUTO_CREATE);
            }
        });
        final Button unbindService = (Button) findViewById(R.id.unbindService);
        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });

        final Button intervalService = (Button) findViewById(R.id.intervalService);
        intervalService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LongRunningService.class);
                startService(intent);
            }
        });



    }




    private MyService.DownloadBinder binder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.DownloadBinder)service;
            binder.startDownload();
            binder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                        // 在这里可以进行UI操作
                    changeText.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String text = editText.getText().toString();
        save(text);
    }


    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data11111111");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    public void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data11111111", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String p1 = data.getStringExtra("p1");
                    String p2 = data.getStringExtra("p2");

                    Toast.makeText(this, p1 + " " + p2, Toast.LENGTH_SHORT).show();
                    Log.d("FirstActivity", p1 + p2);
                }
                break;
            default:
        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
