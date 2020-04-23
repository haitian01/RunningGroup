package com.example.runninggroup.viewAndController.helper;

public class FriendsHelper {
    private int pic;
    private String name;
    private String group;
    private String data;

    public FriendsHelper(int pic, String name, String group, String data) {
        this.pic = pic;
        this.name = name;
        this.group = group;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
