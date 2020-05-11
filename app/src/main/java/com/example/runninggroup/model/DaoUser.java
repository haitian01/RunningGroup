package com.example.runninggroup.model;

import android.util.JsonWriter;

import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


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
      return PostRequest.userRequest("http://10.0.2.2:8080/user/load","username="+username+"&password="+password);
    }
    public static String Register(String username,String password){
        return PostRequest.userRequest("http://10.0.2.2:8080/user/register","username="+username+"&password="+password);
    }
    public static List<FriendsHelper> GetFriends(String username){
        String json =  PostRequest.userRequest("http://10.0.2.2:8080/user/getFriends","username="+username);
        List<FriendsHelper> list = JSONObject.parseArray(json,FriendsHelper.class);
        System.out.println(list);
        return list;
//        System.out.println(PostRequest.userRequest("http://10.0.2.2:8080/user/getFriends", "username=" + username));
//        return PostRequest.userRequest("http://10.0.2.2:8080/user/getFriends","username="+username);

    }

}
