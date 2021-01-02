package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

public class FriendRelationDao {
    private FriendRelationDao(){}
    //获得user集合
    public static List<FriendRelation> getFriendRelation (FriendRelation friendRelation) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_FRIEND_RELATION, JSON.toJSONString(friendRelation));
        return JSONArray.parseArray(json, FriendRelation.class);
    }
    //添加user
    public static boolean addFriendRelation (FriendRelation friendRelation) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_FRIENDR_RELATION, JSON.toJSONString(friendRelation));
        return "succeed".equals(json);
    }
    //更新关系
    public static boolean updateFriendRelation (FriendRelation friendRelation) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.UPDATE_FRIENDR_RELATION, JSON.toJSONString(friendRelation));
        return "succeed".equals(json);
    }
    //删除好友
    public static boolean deleteFriendRelation (int user_id, int friend_id) {
        String json = PostRequest.postRequest(ConstantUtil.URL + ConstantUtil.DELETE_FRIEND_RELATION, "userId=" + user_id + "&friendId=" + friend_id);
        return "succeed".equals(json);
    }


}
