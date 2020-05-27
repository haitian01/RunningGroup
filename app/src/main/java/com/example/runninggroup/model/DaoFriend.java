package com.example.runninggroup.model;

import android.util.Log;

import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;

import org.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.viewAndController.helper.MomentHelper;

import java.util.ArrayList;
import java.util.List;

public class DaoFriend {

    private DaoFriend(){};


    //拿到好友动态列表
    public static List<DynamicHelper> getDynamic(String username){
        String json =  PostRequest.postRequest("http://192.168.0.104:8080/friend/getDynamic","username="+username);
        List<DynamicHelper> list = JSONObject.parseArray(json,DynamicHelper.class);
        if(list != null){
            return list;
        }else {
            return new ArrayList<DynamicHelper>();
        }
    }

    //发送添加好友申请
    public static String insertMoment(String username, String friendName,String content){
        String result =  PostRequest.postRequest("http://192.168.0.104:8080/moment/insertMoment","username="+username+"&friendName="+friendName+"&content="+content+"&moment_time="+Long.toString(System.currentTimeMillis()));
        return result;
    }
    public static List<MomentHelper> queryMomentList(String username){
        String json =  PostRequest.postRequest("http://192.168.0.104:8080/moment/queryMomentList","username="+username);
        Log.v("json----------->",json);
        List<MomentHelper> list = JSONObject.parseArray(json,MomentHelper.class);
        if(list != null){
            for (MomentHelper momentHelper:list){
                Log.v("----------->",momentHelper.toString());
            }
            return list;
        }else {
            return new ArrayList<MomentHelper>();
        }
    }

    //添加好友
    public static String addFriend(String username,String friendName){
        String result =  PostRequest.postRequest("http://192.168.0.104:8080/friend/addFriend","username="+username+"&friendName="+friendName);
        return result;
    }
    //更改状态
    public static String updateProcessed(String username,String friendName){
        String result =  PostRequest.postRequest("http://192.168.0.104:8080/moment/updateProcessed","username="+username+"&friendName="+friendName);
        return result;
    }
    //查询好友关系
    public static boolean queryFriend(String username,String friendName){
        String result =  PostRequest.postRequest("http://192.168.0.104:8080/friend/queryFriend","username="+username+"&friendName="+friendName);
        if(result == "SUCCESS"){
            return true;
        }
        return false;
    }
    //删除好友
    public static boolean deleteFriend(String username,String friendName){
        String result =  PostRequest.postRequest("http://192.168.0.104:8080/friend/deleteFriend","username="+username+"&friendName="+friendName);
        if(result == "SUCCESS"){
            return true;
        }
        return false;
    }


}
