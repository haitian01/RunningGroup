package com.example.runninggroup.request;

import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImgUpload {
    private static String urls = "http://192.168.0.104/user/uploadImg";
    public static Boolean uploadImg(String path,String imgName) {
        try {

            // 1. 获取访问地址URL
            URL url = new URL(urls);
            // 2. 创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数等 */
            // 请求方式
            connection.setRequestMethod("POST");
            // 超时时间
            connection.setConnectTimeout(100);
            // 设置是否输出
            connection.setDoOutput(true);
            // 设置是否读入
            connection.setDoInput(true);
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置使用标准编码格式编码参数的名-值对
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 连接(有问题)
//            connection.connect();
            byte[] bytes = new byte[1024];
            InputStream inputStream = new FileInputStream(new File(path));
            /* 4. 处理输入输出 */
            // 写入参数到请求中
            OutputStream out = connection.getOutputStream();
            while (inputStream.read(bytes) != -1){
                out.write(bytes);
            }
            out.write(-1);
            out.write(imgName.getBytes());
            out.flush();
            out.close();
            // 从连接中读取响应信息
            StringBuffer msg = new StringBuffer();
            String line;
            int code = connection.getResponseCode();
            if (code == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=bufferedReader.readLine()) != null){
                    msg.append(line);
                }
                String result = msg.toString();
                if (result == null) return false;
                else {
                    if ("SUCCESS".equals(result)) return true;
                    return false;
                }


            }
            // 5. 断开连接
            connection.disconnect();

            // 处理结果
            System.out.println(msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
