package com.example.runninggroup.model;

import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.request.GetRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupHelper;

import java.util.List;

public class DaoGroup {

    private DaoGroup(){};
//    private static class Inner{
//        private static DaoUser daoUser = new DaoUser();
//    }
//    public static DaoUser getInstance(){
//        return Inner.daoUser;
//    }

    public static List<GroupHelper> getGroups(){
        String json =  GetRequest.groupRequest("http://10.0.2.2:8080/group/findAll");
        List<GroupHelper> list = JSONObject.parseArray(json,GroupHelper.class);
        return list;
//        System.out.println(PostRequest.userRequest("http://10.0.2.2:8080/user/getFriends", "username=" + username));
//        return PostRequest.userRequest("http://10.0.2.2:8080/user/getFriends","username="+username);

    }

}
