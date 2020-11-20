package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private UserDao(){}
    //获得user集合
    public static List<User> getUser (User user) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_USER, JSON.toJSONString(user));
        return JSONArray.parseArray(json, User.class);
    }
    //添加user
    public static boolean addUser (User user) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_USER, JSON.toJSONString(user));
        return "succeed".equals(json);
    }
    //添加user
    public static boolean updateUser (User user) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.UPDATE_USER, JSON.toJSONString(user));
        return "succeed".equals(json);
    }
    //退出跑团
    public static boolean signOutTeam (User user) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.SIGN_OUT_TEAM, JSON.toJSONString(user));
        return "succeed".equals(json);
    }
    //管理员
    //管理员权限
    public static boolean administrators  (User user) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADMINISTRATORS, JSON.toJSONString(user));
        return "succeed".equals(json);
    }


}
