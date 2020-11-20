package com.example.runninggroup.util;

public class ImgNameUtil {
    //获取用户头像的imgName
    public static String getUserHeadImgName(int id){
        return "user_head_"+id;
    }
    //获取用户头像的imgName
    public static String getUserBackImgName(int id){
        return "user_back_"+id;
    }
    //获取跑团的头像
    public static String getGroupHeadImgName(int id){
        return "team_head_" + id;
    }
    //获取好友某条动态的图片
    public static String getCircleImgName(int id, int position){
        return "circle_" + id + "_" + position;
    }
    //获取跑团某条任务的图片
    public static String getTaskImgName(String admin,long task_time){
        return "task_"+admin+"_"+task_time;
    }
    //获取跑团招募的图片
    public static String getCallImgName(String admin,long task_time){
        return "call_"+admin+"_"+task_time;
    }
    //获取打卡图片
    public static String getCardImgName(String username,long begin_time){
        return "call_"+username+"_"+begin_time;
    }
}
