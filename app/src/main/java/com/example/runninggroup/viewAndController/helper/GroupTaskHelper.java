package com.example.runninggroup.viewAndController.helper;

public class GroupTaskHelper {
    private int img;
    private String release_name;
    private String task_msg;
    private String task_time;

    public GroupTaskHelper() {
    }

    public GroupTaskHelper(int img, String release_name, String task_msg, String task_time) {
        this.img = img;
        this.release_name = release_name;
        this.task_msg = task_msg;
        this.task_time = task_time;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
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

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }
}
