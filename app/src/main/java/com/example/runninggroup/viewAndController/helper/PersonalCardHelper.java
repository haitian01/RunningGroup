package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class PersonalCardHelper {
    private Drawable img;
    String username;
    long begin_time;
    long end_time;
    long length;
    double score;
    String act_type;
    String begin_place;
    String end_place;


    public PersonalCardHelper() {
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getAct_type() {
        return act_type;
    }

    public void setAct_type(String act_type) {
        this.act_type = act_type;
    }

    public String getBegin_place() {
        return begin_place;
    }

    public void setBegin_place(String begin_place) {
        this.begin_place = begin_place;
    }

    public String getEnd_place() {
        return end_place;
    }

    public void setEnd_place(String end_place) {
        this.end_place = end_place;
    }

    @Override
    public String toString() {
        return "PersonalCardHelper{" +
                "img=" + img +
                ", username='" + username + '\'' +
                ", begin_time=" + begin_time +
                ", end_time=" + end_time +
                ", length=" + length +
                ", score=" + score +
                ", act_type='" + act_type + '\'' +
                '}';
    }
}
