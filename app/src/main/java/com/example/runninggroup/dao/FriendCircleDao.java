package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.util.ConstantUtil;



import java.util.List;

public class FriendCircleDao {
    //第一次加载
    public static List<FriendCircle> getFriendCircleFirst () {
        String json = PostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_FRIEND_CIRCLE_First, "");
        return JSONArray.parseArray(json, FriendCircle.class);
    }
    //指定加载
    public static List<FriendCircle> getFriendCircle (int id, int num) {
        String json = PostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_FRIEND_CIRCLE, "id=" + id + "&num=" + num);
        return JSONArray.parseArray(json, FriendCircle.class);
    }
    //发表动态
    public static String  addFriendCircle (FriendCircle friendCircle) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_FRIEND_CIRCLE, JSON.toJSONString(friendCircle));
        return json;
    }

    //点赞
    public static boolean updateZan (int id, int userId) {
        String json = PostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GIVE_ZAN, "userId=" + userId + "&id=" + id);
        return "succeed".equals(json);
    }
}
