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
    //删除teamApplication
    public static boolean deleteFriendApplication (FriendApplication friendApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.DELETE_FRIENDR_APPLICATION, JSON.toJSONString(friendApplication));
        return "succeed".equals(json);
    }

    //开始申请添加好友
    public static boolean startFriendApplication (FriendApplication friendApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.START_FRIENDR_APPLICATION, JSON.toJSONString(friendApplication));
        return "succeed".equals(json);
    }

    //同意/拒绝好友
    public static boolean agreeToRefuse (FriendApplication friendApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.AGREE_TO_REFUSE, JSON.toJSONString(friendApplication));
        return "succeed".equals(json);

    }


}
