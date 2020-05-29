package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class User {
    private int id;
    private String username;
    private String password;
    private int groupID;
    private int score;
    private Drawable pic;
    private String sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Drawable getPic() {
        return pic;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
