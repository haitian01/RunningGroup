package com.example.runninggroup.util;

public class ConstantUtil {
    private ConstantUtil () {}
    //app后端版本（对应不同的后端）
    public final static int VERSION = 1;
    public final static String URL = "http://192.168.43.222:8080";
    //用户表controller
    public final static String ADD_USER = "/user/addUser";
    public final static String UPDATE_USER = "/user/updateUser";
    public final static String GET_USER = "/user/getUser";

    //活动表controller
    public final static String ADD_ACT = "/act/addAct";
    public final static String UPDATE_ACT = "/act/updateAct";
    public final static String GET_ACT = "/act/getAct";

    //好友表controller
    public final static String ADD_FRIENDR_RELATION = "/friendRelation/addFriendRelation";
    public final static String UPDATE_FRIENDR_RELATION = "/friendRelation/updateFriendRelation";
    public final static String GET_FRIENDR_RELATION = "/friendRelation/getFriendRelation";

    //朋友圈controller
    public final static String ADD_FRIENDR_CIRCLE = "/friendCircle/addFriendCircle";
    public final static String UPDATE_FRIENDR_CIRCLE = "/friendCircle/updateFriendCircle";
    public final static String GET_FRIENDR_CIRCLE = "/friendCircle/getFriendCircle";

    //分数表controller
    public final static String ADD_SCORE = "/score/addScore";
    public final static String UPDATE_SCORE = "/score/updateScore";
    public final static String GET_SCORE = "/score/getScore";

    //跑团表controller
    public final static String ADD_TEAM = "/team/addTeam";
    public final static String UPDATE_TEAM = "/team/updateTeam";
    public final static String GET_TEAM = "/team/getTeam";

    //跑团任务表 controller
    public final static String ADD_TEAM_TASK= "/teamTask/addTeamTask";
    public final static String UPDATE_TEAM_TASK = "/teamTask/updateTeamTask";
    public final static String GET_TEAM_TASK = "/teamTask/getTeamTask";

    //文件上传
    public final static String UPLOAD = "/file/upload";
    public final static String GET_IMG = "/file/getImg";



}
