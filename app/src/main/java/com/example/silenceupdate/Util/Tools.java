package com.example.silenceupdate.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Tools {

    //获取apk的版本号 currentVersionCode
    public static int getAPPLocalVersion(Context ctx) {
        PackageManager manager = ctx.getPackageManager();
        int localVersionCode = 0;
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            localVersionCode = info.versionCode; // 版本号
            return localVersionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAPKLocalVersion(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            try {
                return info.versionCode;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
