package com.example.silenceupdate;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.silenceupdate.Util.Tools;

public class UpdateRestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Logger", "onReceive: "+intent.getAction());
        String packageName = intent.getDataString();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {//接收自己app升级广播
            Log.i("Logger",  "onReceive:重启" + packageName);
            Intent intent2 = new Intent(context, MainActivity.class);//重启app
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {//接收安装广播
            Log.i("Logger",  "onReceive:安装了" + packageName);

        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) { //接收卸载广播
            Log.i("Logger",   "onReceive:卸载了" + packageName+Tools.getAPPLocalVersion(context));
        }
    }

}