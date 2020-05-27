package com.example.runninggroup.viewAndController.helper;

public class DynamicHelper {
    private String dynamic_img;
    private String dynamic_msg;
    private String dynamic_time;

    public DynamicHelper() {
    }

    public DynamicHelper(String dynamic_img, String dynamic_msg, String dynamic_time) {
        this.dynamic_img = dynamic_img;
        this.dynamic_msg = dynamic_msg;
        this.dynamic_time = dynamic_time;
    }

    public String getDynamic_img() {
        return dynamic_img;
    }

    public void setDynamic_img(String dynamic_img) {
        this.dynamic_img = dynamic_img;
    }

    public String getDynamic_msg() {
        return dynamic_msg;
    }

    public void setDynamic_msg(String dynamic_msg) {
        this.dynamic_msg = dynamic_msg;
    }

    public String getDynamic_time() {
        return dynamic_time;
    }

    public void setDynamic_time(String dynamic_time) {
        this.dynamic_time = dynamic_time;
    }
}
