package com.example.runninggroup.viewAndController.helper;

import android.graphics.drawable.Drawable;

public class GroupHelper {
    private Drawable logo;
    private String groupName;
    private int numbers;
    private String leaderName;
    private String slogan;

    public Drawable getLogo() {
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
