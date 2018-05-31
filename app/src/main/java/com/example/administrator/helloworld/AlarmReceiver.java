package com.example.administrator.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "AlarmReceiver received !", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
    }
}
