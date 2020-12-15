package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.TeamNotice;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

public class TeamNoticeDao {
    /**
     * 天机一条公告
     * @param teamNotice
     * @return
     */
    public static String addTeamNotice(TeamNotice teamNotice) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_TEAM_NOTICE, JSON.toJSONString(teamNotice));
        return json;
    }

    /**
     * 拉去全部
     * @param teamNotice
     * @return
     */
    public static List<TeamNotice> getTeamNotice (TeamNotice teamNotice) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_TEAM_NOTICE, JSON.toJSONString(teamNotice));
        return JSONArray.parseArray(json, TeamNotice.class);
    }

    /**
     * 拉取指定条数
     * @param team_id 跑团id
     * @param limit 条数
     * @return
     */

    public static List<TeamNotice> getTeamNoticeLimit (int team_id, int limit) {
        String json = PostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_TEAM_NOTICE_LIMIT, "teamId=" + team_id + "&limit=" + limit);
        return JSONArray.parseArray(json, TeamNotice.class);
    }

}
