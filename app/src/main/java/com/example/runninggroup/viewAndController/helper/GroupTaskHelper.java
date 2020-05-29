package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class GroupTaskHelper {
    private Drawable img;
    private String release_name;
    private String task_msg;
    private long task_time;

    public GroupTaskHelper() {
    }

    public GroupTaskHelper(Drawable img, String release_name, String task_msg, long task_time) {
        this.img = img;
        this.release_name = release_name;
        this.task_msg = task_msg;
        this.task_time = task_time;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getRelease_name() {
        return release_name;
    }

    public void setRelease_name(String release_name) {
        this.release_name = release_name;
    }

    public String getTask_msg() {
        return task_msg;
    }

    public void setTask_msg(String task_msg) {
        this.task_msg = task_msg;
    }

    public long getTask_time() {
        return task_time;
    }

    public void setTask_time(long task_time) {
        this.task_time = task_time;
    }
}
