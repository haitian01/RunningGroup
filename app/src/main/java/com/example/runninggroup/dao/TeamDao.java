package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

public class TeamDao {
    private TeamDao(){}
    //获得team集合
    public static List<Team> getTeam (Team team) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_TEAM, JSON.toJSONString(team));
        return JSONArray.parseArray(json, Team.class);
    }
    //添加team
    public static boolean addTeam (Team team) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_TEAM, JSON.toJSONString(team));
        return "succeed".equals(json);
    }

    //添加team
    public static User buildTeam (Team team) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.BUILD_TEAM, JSON.toJSONString(team));
        User user = (User) JSON.parseObject(json, User.class);
        return user;
    }
    //添加team
    public static boolean updateTeam (Team team) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.UPDATE_TEAM, JSON.toJSONString(team));
        return "succeed".equals(json);
    }


}
