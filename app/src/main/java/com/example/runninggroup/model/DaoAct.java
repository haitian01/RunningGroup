package com.example.runninggroup.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.request.GetRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.helper.GroupHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.util.List;

public class DaoAct {

    private DaoAct(){};


    //拿到某时间段跑步长度
    public static String getLength(String username,long beginTime,long endTime){
        String length =  PostRequest.postRequest("http://39.97.66.19:8080/act/getLength","username="+username+"&beginTime="+beginTime+"&endTime="+endTime);
        if (length == null) return "0";
        return length;
    }
    //插入一条活动记录
    public static boolean insertAct(String username,long beginTime,long endTime,long length,double score){
        String result =  PostRequest.postRequest("http://39.97.66.19:8080/act/insertAct","username="+username+"&beginTime="+beginTime+"&endTime="+endTime+"&length="+length+"&score="+score);
        if (result == null) return false;
        else {
            if("SUCCESS".equals(result)){return true;}
            return false;
        }
    }


}
