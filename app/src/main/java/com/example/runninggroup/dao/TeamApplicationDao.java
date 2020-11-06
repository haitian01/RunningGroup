package com.example.runninggroup.dao;

import android.graphics.drawable.Drawable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.TeamApplication;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.io.File;
import java.util.List;

public class TeamApplicationDao {
    //发送跑团申请
    public static boolean addTeamApplication (TeamApplication teamApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_TEAM_APPLICATION, JSON.toJSONString(teamApplication));
        return "succeed".equals(json) ? true : false;
    }
    public static boolean updateTeamApplication (TeamApplication teamApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.UPDATE_TEAM_APPLICATION, JSON.toJSONString(teamApplication));
        return "succeed".equals(json) ? true : false;
    }
    public static List<TeamApplication> getTeamApplication (TeamApplication teamApplication) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_TEAM_APPLICATION, JSON.toJSONString(teamApplication));
        return JSONArray.parseArray(json, TeamApplication.class);
    }
}
