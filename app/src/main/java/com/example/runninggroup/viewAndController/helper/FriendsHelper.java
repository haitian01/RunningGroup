package com.example.runninggroup.viewAndController.helper;

public class FriendsHelper {
    private int pic;
    private String name;
    private String group;
    private long length;
    private int score;

    public FriendsHelper(int pic, String name, String group, long length, int score) {
        this.pic = pic;
        this.name = name;
        this.group = group;
        this.length = length;
        this.score = score;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
}
