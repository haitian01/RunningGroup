package com.example.runninggroup.request;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ImgUpload {
    public static String uploadFile(File file,String imgName) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "---------------------------823928434";
        try {
            URL url = new URL("http://39.97.66.19:8080/user/uploadImg");
//            URL url = new URL("http://192.168.0.104:8080/run/user/uploadImg");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"file2\"; filename="+imgName+".jpg" + end);
            dos.writeBytes(end);

            byte[] bytes = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            while (inputStream.read(bytes) != -1) {
                dos.write(bytes);
            }
            inputStream.close();
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            dos.close();
            // 读取服务器返回结果
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            is.close();
            if(result != null) return  result;
            else return "NullPointer";

        } catch (IOException e) {
            e.printStackTrace();
            return "Exception";
        }

    }
    public static String uploadFileNative(File file,String imgName) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "---------------------------823928434";
        try {
//            URL url = new URL("http://39.97.66.19:8080/user/uploadImg");
            URL url = new URL("http://192.168.0.104:8080/run/user/uploadImg");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"file2\"; filename="+imgName+".jpg" + end);
            dos.writeBytes(end);
            byte[] bytes = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            while (inputStream.read(bytes) != -1) {
                dos.write(bytes);
            }
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            // 读取服务器返回结果
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            is.close();
            if(result != null) return  result;
            else return "NullPointer";

        } catch (IOException e) {
            e.printStackTrace();
            return "Exception";
        }

    }
    

}
