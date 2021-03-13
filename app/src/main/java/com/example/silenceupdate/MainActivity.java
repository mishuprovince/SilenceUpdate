package com.example.silenceupdate;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.silenceupdate.Util.Shell;
import com.example.silenceupdate.Util.SystemUtil;
import com.example.silenceupdate.Util.Tools;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String TAG = "mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = this.findViewById(R.id.version);

        int oldVersionCode = 0;
        int newVersionCode = 0;
        String apkPath = Environment.getExternalStorageDirectory() + "/Space365/new.apk";

        oldVersionCode = Tools.getAPPLocalVersion(this.getBaseContext());
        newVersionCode = Tools.getAPKLocalVersion(this.getBaseContext(), apkPath);

        textView.setText("当前版本号为：" + oldVersionCode + "apk版本号为：" + newVersionCode);
        int finalOldVersionCode = oldVersionCode;
        int finalNewVersionCode = newVersionCode;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (finalOldVersionCode < finalNewVersionCode) {
                        installApk();
                        Thread.sleep(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void installApk() {

        File file = new File(Environment.getExternalStorageDirectory(), "Space365/new.apk");

        if (!Shell.execWithRootPrivilege(String.format("chmod 755 %s", file.getAbsolutePath()))) {

            throw new IllegalStateException();
        }

        String installCommand;
        if (SystemUtil.getSystemVersionCode() < 25) {
            installCommand = String.format("pm install -r %s", file.getAbsoluteFile());
        } else {
            installCommand = String.format("pm install -r -i %s --user 0 %s", this.getPackageName(), file.getAbsoluteFile());
        }

        if (!Shell.execWithRootPrivilege(installCommand)) {
            throw new IllegalStateException();
        }
    }

}