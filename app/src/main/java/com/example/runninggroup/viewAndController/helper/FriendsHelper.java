package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class FriendsHelper {
    private Drawable pic;
    private String username;
    private String groupName;
    private long length;
    private int score;
    public FriendsHelper(){}

    public FriendsHelper(Drawable pic, String username, String groupName, long length, int score) {
        this.pic = pic;
        this.username = username;
        this.groupName = groupName;
        this.length = length;
        this.score = score;
    }

    public Drawable getPic() {
        return pic;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "FriendsHelper{" +
                "pic=" + pic +
                ", username='" + username + '\'' +
                ", groupName='" + groupName + '\'' +
                ", length=" + length +
                ", score=" + score +
                '}';
    }
}
