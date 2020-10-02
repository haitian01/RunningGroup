package com.example.runninggroup.controller;

import android.graphics.drawable.Drawable;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FileDao;

import java.io.File;

public class FileController {
    private FileControllerInterface mFileControllerInterface;
    private String username = Cache.user.getUsername();
    public FileController (FileControllerInterface fileControllerInterface) {
        mFileControllerInterface = fileControllerInterface;
    }

    //上传图片
    public void upload (File file, String imgName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = FileDao.upload(file, imgName);
                mFileControllerInterface.uploadBack(res);
            }
        }).start();
    }
    //获取图片
    public void getImg (String imgName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = FileDao.getImg(imgName);
                mFileControllerInterface.getImgBack(drawable);
            }
        }).start();
    }



    public interface FileControllerInterface {
        void uploadBack(boolean res);
        void getImgBack(Drawable drawable);
    }
}
