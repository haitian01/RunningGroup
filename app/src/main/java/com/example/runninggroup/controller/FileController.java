package com.example.runninggroup.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.util.BitmapUtil;
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
    public void uploadMore (Activity activity, List<Bitmap> bitmaps, Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = true;
                for (int i = 0; i < bitmaps.size(); i++) {
                    //压缩
                    File file = BitmapUtil.copyFile(activity, bitmaps.get(i), ImgNameUtil.getCircleImgName(id, i));
//                    File file = new File(paths.get(i));
                   boolean upload = FileDao.upload(file, ImgNameUtil.getCircleImgName(id, i));
                   if (! upload) res = false;
                }
                mFileControllerInterface.uploadMoreBack(res);
            }
        }).start();

    }

    //上传多张(公告)
    public void uploadMoreNotice (Activity activity, List<Bitmap> bitmaps, Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = true;
                for (int i = 0; i < bitmaps.size(); i++) {
                    //压缩
                    File file = BitmapUtil.copyFile(activity, bitmaps.get(i), ImgNameUtil.getCircleImgName(id, i));
//                    File file = new File(paths.get(i));
                    boolean upload = FileDao.upload(file, ImgNameUtil.getNoticeImgName(id, i));
                    if (! upload) res = false;
                }
                mFileControllerInterface.uploadMoreNoticeBack(res);
            }
        }).start();

    }
    //获取图片
    public void getImg (String imgName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = null;
                for (int i = 0; i < 10; i++) {
                    if (drawable != null) break;
                    else drawable = FileDao.getImg(imgName);
                }
                mFileControllerInterface.getImgBack(drawable);
            }
        }).start();
    }
    //获取图片（带有标识的）
    public void getImg (String imgName, int mark) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = null;
                for (int i = 0; i < 10; i++) {
                    if (drawable != null) break;
                    else drawable = FileDao.getImg(imgName);
                }
                mFileControllerInterface.getImgBack(drawable, mark);
            }
        }).start();
    }



    public interface FileControllerInterface {
       default void uploadBack(boolean res){};
       default void uploadMoreBack(boolean res){};
       default void getImgBack(Drawable drawable){};
       default void getImgBack(Drawable drawable, int mark){};

        default void uploadMoreNoticeBack(boolean res){};
    }
}
