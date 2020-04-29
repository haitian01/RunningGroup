package com.example.runninggroup.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyHttpRequest {
    public static void getwebinfo() {
        try {
            //1,找水源--创建URL
            URL url = new URL("http://10.0.2.2:8080/one/hello/");//放网站


            //2,开水闸--openConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");//post 请求
            httpURLConnection.setConnectTimeout(1000*5);
            httpURLConnection.setReadTimeout(1000*5);
            httpURLConnection.setDoInput(true);//允许从服务端读取数据
            httpURLConnection.setDoOutput(true);//允许写入
            //3，建管道--InputStream
            InputStream inputStream = httpURLConnection.getInputStream();
            //4，建蓄水池蓄水-InputStreamReader
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            //5，水桶盛水--BufferedReader
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer buffer = new StringBuffer();
            String temp = null;
            int code =  httpURLConnection.getResponseCode();
            if(code == 200){
                while ((temp = bufferedReader.readLine()) != null) {
                    //取水--如果不为空就一直取
                    buffer.append(temp);
                }
                bufferedReader.close();//记得关闭
                reader.close();
                inputStream.close();
                Log.e("MAIN",buffer.toString());//打印结果
            }else {
                Log.e("MAIN","连接失败");//打印结果
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
