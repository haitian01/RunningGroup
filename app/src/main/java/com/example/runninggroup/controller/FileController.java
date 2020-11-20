package com.example.runninggroup.controller;

import android.graphics.drawable.Drawable;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.util.ImgNameUtil;

import java.io.File;
import java.util.List;

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

    //上传多张
    public void uploadMore (List<String> paths, Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = true;
                for (int i = 0; i < paths.size(); i++) {
                   boolean upload = FileDao.upload(new File(paths.get(i)), ImgNameUtil.getCircleImgName(id, i));
                   if (! upload) res = false;
                }
                mFileControllerInterface.uploadMoreBack(res);
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
       default void uploadBack(boolean res){};
       default void uploadMoreBack(boolean res){};
       default void getImgBack(Drawable drawable){};
    }
}
