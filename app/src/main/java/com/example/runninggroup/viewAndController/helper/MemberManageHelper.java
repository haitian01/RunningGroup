package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class MemberManageHelper {
    Drawable img;
    String username;
    int admin;

    public MemberManageHelper() {
    }

    public MemberManageHelper(Drawable img, String username, int admin) {
        this.img = img;
        this.username = username;
        this.admin = admin;
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

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
}
