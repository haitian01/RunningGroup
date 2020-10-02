package com.example.runninggroup.cache;

import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    public static User user = new User();
    public static List<FriendRelation> friendRelationList = new ArrayList<>();
    public static User friend = new User();
}
