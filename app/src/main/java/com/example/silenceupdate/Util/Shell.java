package com.example.silenceupdate.Util;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
    public static boolean exec(String command) {
        return exec(command, null);
    }

    public static String execWithResultMessage(String command) {
        ResultMessageWrapper wrapper = new ResultMessageWrapper();
        boolean result = exec(command, wrapper);
        if (result) {
            return wrapper.std;
        }
        return null;
    }

    public synchronized static boolean exec(String command, ResultMessageWrapper wrapper) {


        Process process = null;

        try {
            process = Runtime.getRuntime().exec(command);

            BufferedReader std = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            int resultCode = process.waitFor();

            if (wrapper != null) {
                wrapper.std = readMessage(std).trim();
                wrapper.error = readMessage(error).trim();
            }
            return resultCode == 0 ? true : false;
        } catch (IOException | InterruptedException e) {
            // ignore
            return false;
        } finally {
            try {
                process.destroy();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    static class ResultMessageWrapper {
        public String std;
        public String error;
    }

    /**
     * 在设备上以root执行command
     * eg: executeWithRootPrivilege("pm install -r /sdcard/Download/meetings.apk");
     * 等同于执行su pm install ...
     *
     * @param command
     * @return 当process返回值为0时返回true
     * @throws
     */
    public synchronized static boolean execWithRootPrivilege(String command) {

        Process process = null;
        DataOutputStream outputStream = null;
        BufferedReader errorReader = null;

        try {

            process = Runtime.getRuntime().exec("su");
            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            outputStream = new DataOutputStream(process.getOutputStream());
            outputStream.writeBytes(command + "\n");
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            int resultCode = process.waitFor();

            String errorMessage = readMessage(errorReader);


            return resultCode == 0 ? true : false;
        } catch (IOException | InterruptedException e) {
            // ignore
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    //读取执行命令后返回的信息
    public static String readMessage(BufferedReader messageReader) throws IOException {
        StringBuilder content = new StringBuilder();
        String lineString;
        while ((lineString = messageReader.readLine()) != null) {
            content.append(lineString).append("\n");
        }

        return content.toString();
    }
}
