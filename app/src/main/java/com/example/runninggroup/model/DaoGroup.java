package com.example.runninggroup.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.request.GetRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.text.DateFormatSymbols;
import java.util.List;

public class DaoGroup {

    private DaoGroup(){};
//    private static class Inner{
//        private static DaoUser daoUser = new DaoUser();
//    }
//    public static DaoUser getInstance(){
//        return Inner.daoUser;
//    }
    //拿到所有的跑团
    public static List<GroupHelper> getGroups(){
        String json =  GetRequest.getRequest("http://192.168.0.104:8080/group/findAll");
        List<GroupHelper> list = JSONObject.parseArray(json,GroupHelper.class);
        return list;

    }
    //拿到某个跑团的所有任务信息
    public static List<GroupTaskHelper> getGroupTask(String groupName){
        String json =  PostRequest.postRequest("http://192.168.0.104:8080/group/findAllTask","groupName="+groupName);
        Log.v("json",json);
        List<GroupTaskHelper> list = JSONObject.parseArray(json,GroupTaskHelper.class);
        Log.v("list",list.toString()+"");
        return list;
    }
    //拿到某个跑团的所有任务信息
    public static String getLeader(String groupName){
        String leader =  PostRequest.postRequest("http://192.168.0.104:8080/group/getLeader","groupName="+groupName);
        return leader;
    }
    //发布跑团任务
    public static String addTask(String groupName,String releaseName,String task){
        long time = System.currentTimeMillis();
        String code =  PostRequest.postRequest("http://192.168.0.104:8080/group/addTask","groupName="+groupName+"&releaseName="+releaseName+"&task="+task+"&time="+time);
        return code;
    }
    //创建跑团
    public static String addGroup(String groupName,String leaderName,byte[] logo,String slogan){
        String code =  PostRequest.postRequest("http://192.168.0.104:8080/group/addGroup","groupName="+groupName+"&leaderName="+leaderName+"&slogan="+slogan);
        return code;
    }

}
