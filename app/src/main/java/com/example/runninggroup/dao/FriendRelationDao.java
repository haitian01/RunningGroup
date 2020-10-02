package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

public class FriendRelationDao {
    private FriendRelationDao(){}
    //获得user集合
    public static List<FriendRelation> getFriendRelation (FriendRelation friendRelation) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_FRIENDR_RELATION, JSON.toJSONString(friendRelation));
        return JSONArray.parseArray(json, FriendRelation.class);
    }
    //添加user
    public static boolean addFriendRelation (FriendRelation friendRelation) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_FRIENDR_RELATION, JSON.toJSONString(friendRelation));
        return "succeed".equals(json);
    }
    //添加user
    public static boolean updateFriendRelation (FriendRelation friendRelation) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.UPDATE_FRIENDR_RELATION, JSON.toJSONString(friendRelation));
        return "succeed".equals(json);
    }


}
