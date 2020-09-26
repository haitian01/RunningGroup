package com.example.runninggroup.util;

public class ImgNameUtil {
    //获取用户头像的imgName
    public static String getUserHeadImgName(int id){
        return "user_head_"+id;
    }
    //获取跑团的头像
    public static String getGroupHeadImgName(String groupName){
        return "group_"+ CharacterUtil.toUNICODE(groupName) +"_head";
    }
    //获取好友某条动态的图片
    public static String getDynamicImgName(String username,long dynamic_time){
        return "dynamic_"+username+"_"+dynamic_time;
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
