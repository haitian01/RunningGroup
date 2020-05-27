package com.example.runninggroup.model;

import android.util.JsonWriter;
import android.util.Log;

import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.viewAndController.helper.GroupHelper;
import com.example.runninggroup.viewAndController.helper.User;


import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class DaoUser {

    private DaoUser(){};
//    private static class Inner{
//        private static DaoUser daoUser = new DaoUser();
//    }
//    public static DaoUser getInstance(){
//        return Inner.daoUser;
//    }
    public static String isLoad(String username,String password){
      return PostRequest.postRequest("http://192.168.0.104:8080/user/load","username="+username+"&password="+password);
    }
    public static String register(String username,String password,String sex){
        String a =  PostRequest.postRequest("http://192.168.0.104:8080/user/register","username="+username+"&password="+password+"&sex="+sex);
        Log.v("TAG",a);
        return a;
    }
    public static List<FriendsHelper> getFriends(String username){
        String json =  PostRequest.postRequest("http://192.168.0.104:8080/user/getFriends","username="+username);
        List<FriendsHelper> list = JSONObject.parseArray(json,FriendsHelper.class);
        if(list != null){
            return list;
        }
        return new ArrayList<FriendsHelper>();
    }
    public static String getMyGroup(String username){
        String group =  PostRequest.postRequest("http://192.168.0.104:8080/user/getMyGroup","username="+username);
        return group;
    }

    public static List<GroupHelper> getMyGroupAll(String username){
        String group =  PostRequest.postRequest("http://192.168.0.104:8080/user/getMyGroupAll","username="+username);
        System.out.println(group);
        List<GroupHelper> list = JSONObject.parseArray(group, GroupHelper.class);
        return list;
    }
    public static String setMyGroup(String username,String groupName,int change,String type){
        String group =  PostRequest.postRequest("http://192.168.0.104:8080/user/setMyGroup","username="+username+"&groupName="+groupName+"&change="+change+"&type="+type);
        return group;
    }

    public static List<User> getAllMember(String groupName){
        String json =  PostRequest.postRequest("http://192.168.0.104:8080/user/getAllMember","groupName="+groupName);
        List<User> list = JSONObject.parseArray(json,User.class);
        return list;
    }

}
