package com.example.runninggroup.dao;

import android.graphics.drawable.Drawable;

import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.request.ImgUpload;

import java.io.File;

public class FileDao {
    //上传文件/图片
    public static boolean upload (File file, String imgName) {
        String res = ImgUpload.uploadFileNative(file, imgName);
        if ("succeed".equals(res)) return true;
        return false;
    }

    //获得文件/图片
    public static Drawable getImg (String imgName) {
         return ImgGet.getImg(imgName);
    }
}
