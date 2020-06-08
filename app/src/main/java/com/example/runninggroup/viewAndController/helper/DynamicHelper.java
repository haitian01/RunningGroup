package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class DynamicHelper {
    private Drawable dynamic_img;
    private String dynamic_msg;
    private long dynamic_time;

    public DynamicHelper() {
    }

    public DynamicHelper(Drawable dynamic_img, String dynamic_msg, long dynamic_time) {
        this.dynamic_img = dynamic_img;
        this.dynamic_msg = dynamic_msg;
        this.dynamic_time = dynamic_time;
    }

    public Drawable getDynamic_img() {
        return dynamic_img;
    }

    public void setDynamic_img(Drawable dynamic_img) {
        this.dynamic_img = dynamic_img;
    }

    public String getDynamic_msg() {
        return dynamic_msg;
    }

    public void setDynamic_msg(String dynamic_msg) {
        this.dynamic_msg = dynamic_msg;
    }

    public long getDynamic_time() {
        return dynamic_time;
    }

    public void setDynamic_time(long dynamic_time) {
        this.dynamic_time = dynamic_time;
    }
}
