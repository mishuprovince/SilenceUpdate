"Android 静默更新"

1. 添加权限

```<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.example.silenceupdate"
    android:sharedUserId="android.uid.system">
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
    <uses-permission android:name="android.permission.RESTART_PACKAGES" />
    <uses-permission android:name="android.permission.INSTALL_PACKAGES"
        tools:ignore="ProtectedPermissions" /> 
```
  
2. 注册广播

``` <receiver android:name=".UpdateRestartReceiver">
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_REPLACED"/>
                <action android:name="android.intent.action.PACKAGE_ADDED" />
                <action android:name="android.intent.action.PACKAGE_REMOVED" />
                <action android:name="android.intent.action.MY_PACKAGE_REPLACED" />
                <data android:scheme="package" />
            </intent-filter>
        </receiver> 
 ```
        
 
 3. 接受广播
 
 ``` public class UpdateRestartReceiver extends BroadcastReceiver {
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
```
