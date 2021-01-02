package com.example.runninggroup.cache;

import android.graphics.drawable.Drawable;

import com.example.runninggroup.pojo.Act;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamNotice;
import com.example.runninggroup.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    public static User user = new User();
    public static List<FriendRelation> friendRelationList = new ArrayList<>();
    public static User friend = new User();
    public static Team team = new Team();
    public static ConcurrentHashMap<Integer ,Drawable> sDrawables = new ConcurrentHashMap<>();
    public static TeamNotice teamNotice = new TeamNotice();
    public static Act act;
}
