package com.example.runninggroup.viewAndController.helper;

public class MemberManageHelper {
    String img;
    String username;
    int admin;

    public MemberManageHelper() {
    }

    public MemberManageHelper(String img, String username, int admin) {
        this.img = img;
        this.username = username;
        this.admin = admin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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
