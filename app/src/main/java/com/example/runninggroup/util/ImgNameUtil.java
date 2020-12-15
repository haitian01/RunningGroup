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

    //获取跑团公告
    public static String getNoticeImgName(int id, int position){
        return "notice_" + id + "_" + position;
    }

}
