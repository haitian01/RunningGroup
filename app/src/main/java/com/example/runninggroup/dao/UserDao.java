package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

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


}
