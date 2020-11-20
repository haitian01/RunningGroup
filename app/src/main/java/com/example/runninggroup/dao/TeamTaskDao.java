package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamTask;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

public class TeamTaskDao {
    public static boolean addTeamTask (TeamTask teamTask) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_TEAM_TASK, JSON.toJSONString(teamTask));
        return "succeed".equals(json) ? true : false;
    }
    public static boolean deleteTeamTask (TeamTask teamTask) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.DELETE_TEAM_TASK, JSON.toJSONString(teamTask));
        return "succeed".equals(json) ? true : false;
    }
    public static List<TeamTask> getTeamTask (TeamTask teamTask) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.DELETE_TEAM_TASK, JSON.toJSONString(teamTask));
        return JSONArray.parseArray(json, TeamTask.class);
    }
}
