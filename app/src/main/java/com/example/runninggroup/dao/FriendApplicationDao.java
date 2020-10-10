package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

public class FriendApplicationDao {
    private FriendApplicationDao(){}
    //获得friendApplication集合
    public static List<FriendApplication> getFriendApplication (FriendApplication friendApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_FRIENDR_APPLICATION, JSON.toJSONString(friendApplication));
        return JSONArray.parseArray(json, FriendApplication.class);
    }
    //添加friendApplication
    public static boolean addFriendApplication (FriendApplication friendApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_FRIENDR_APPLICATION, JSON.toJSONString(friendApplication));
        return "succeed".equals(json);
    }
    //更新friendApplication
    public static boolean updateFriendApplication (FriendApplication friendApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.UPDATE_FRIENDR_APPLICATION, JSON.toJSONString(friendApplication));
        return "succeed".equals(json);
    }


}
