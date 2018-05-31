package com.example.administrator.helloworld;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/4/12.
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "received in ForceOfflineReceiver", Toast.LENGTH_SHORT).show();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Warning");
        dialogBuilder.setMessage("You are forced to be offline. Please try to login again.");
        dialogBuilder.setCancelable(false);

        //点击对话框OK
        dialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll(); // 销毁所有活动
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent); // 重新启动LoginActivity
                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        // 需要设置AlertDialog的类型，保证在广播接收器中可以正常弹出
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alertDialog.show();

       // context.unregisterReceiver(this);
    }
}

