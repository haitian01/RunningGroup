package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class MomentHelper {
    private Drawable pic;
    String from_name;
    String content;
    long moment_time;

    public MomentHelper() {
    }

    public MomentHelper(String from_name, String content, long moment_time,Drawable pic) {
        this.from_name = from_name;
        this.content = content;
        this.moment_time = moment_time;
        this.pic = pic;
    }

    public Drawable getPic() {
        return pic;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMoment_time() {
        return moment_time;
    }

    public void setMoment_time(long moment_time) {
        this.moment_time = moment_time;
    }

    @Override
    public String toString() {
        return "MomentHelper{" +
                "from_name='" + from_name + '\'' +
                ", content='" + content + '\'' +
                ", moment_time=" + moment_time +
                '}';
    }
}
