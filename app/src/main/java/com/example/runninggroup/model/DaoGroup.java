package com.example.runninggroup.model;

import android.telephony.mbms.GroupCall;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.request.GetRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.MemberManage;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupCallHelper;
import com.example.runninggroup.viewAndController.helper.GroupHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;
import com.example.runninggroup.viewAndController.helper.MemberManageHelper;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
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
        String json =  GetRequest.getRequest("http://39.97.66.19:8080/group/findAll");
        List<GroupHelper> list = JSONObject.parseArray(json,GroupHelper.class);
        if(list != null){
            return list;
        }
        return new ArrayList<GroupHelper>();

    }
    //拿到某个跑团的所有任务信息
    public static List<GroupTaskHelper> getGroupTask(String groupName){
        String json =  PostRequest.postRequest("http://39.97.66.19:8080/group/findAllTask","groupName="+groupName);
        List<GroupTaskHelper> list = JSONObject.parseArray(json,GroupTaskHelper.class);
        if(list != null){
            return list;
        }
        return new ArrayList<GroupTaskHelper>();
    }
    //拿到某个跑团的leader
    public static String getLeader(String groupName){
        String leader =  PostRequest.postRequest("http://39.97.66.19:8080/group/getLeader","groupName="+groupName);
        if (leader != null){
            return leader;
        }
        return "";
    }
    //发布跑团任务
    public static String addTask(String groupName,String releaseName,String task,long time){
        String code =  PostRequest.postRequest("http://39.97.66.19:8080/group/addTask","groupName="+groupName+"&releaseName="+releaseName+"&task="+task+"&time="+time);
        if (code == null) return "ERROR";
        return code;
    }
    //创建跑团
    public static String addGroup(String groupName,String leaderName,byte[] logo,String slogan){
        String code =  PostRequest.postRequest("http://39.97.66.19:8080/group/addGroup","groupName="+groupName+"&leaderName="+leaderName+"&slogan="+slogan);
        if (code == null) return "ERROR";
        return code;
    }


    //拿到某个跑团所有招募信息
    public static List<GroupCallHelper> getGroupCall(String groupName){
        String json =  PostRequest.postRequest("http://39.97.66.19:8080/group/getGroupCall","groupName="+groupName);
        List<GroupCallHelper> list = JSONObject.parseArray(json,GroupCallHelper.class);
        if(list != null){
            return list;
        }
        return new ArrayList<GroupCallHelper>();
    }
    //发布招募信息
    public static boolean addGroupCall(String groupName,String releaseName,String call){
        long time = System.currentTimeMillis();
        String result =  PostRequest.postRequest("http://39.97.66.19:8080/group/addGroupCall","groupName="+groupName+"&releaseName="+releaseName+"&call="+call+"&time="+time);
        if(result == null) return false;
        if("SUCCESS".equals(result)) return true;
        return false;
    }
    //解散某个跑团
    public static boolean dismissGroup(String groupName){
        String result =  PostRequest.postRequest("http://39.97.66.19:8080/group/dismissGroup","groupName="+groupName);
        if(result == null) return false;
        if("SUCCESS".equals(result)) return true;
        return false;
    }
    //拿到成员权限列表
    public static List<MemberManageHelper> getMemberTitle(String groupName){
        String json =  PostRequest.postRequest("http://39.97.66.19:8080/group/getMemberTitle","groupName="+groupName);
        List<MemberManageHelper> list = JSONObject.parseArray(json,MemberManageHelper.class);
        if(list != null){
            return list;
        }
        return new ArrayList<MemberManageHelper>();
    }
    //踢出跑团成员
    public static boolean removeSb(String groupName,String member){
        String result =  PostRequest.postRequest("http://39.97.66.19:8080/group/removeSb","groupName="+groupName+"&member="+member);
        if(result == null) return false;
        if("SUCCESS".equals(result)) return true;
        return false;
    }
    //设置管理员权限
    public static boolean setAdmin(String groupName,String member,int admin){
        String result =  PostRequest.postRequest("http://39.97.66.19:8080/group/setAdmin","groupName="+groupName+"&member="+member+"&admin="+admin);
        if (result == null) return false;
        if("SUCCESS".equals(result)) return true;
        return false;
    }

}
