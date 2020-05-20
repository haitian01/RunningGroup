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
        String length =  PostRequest.postRequest("http://192.168.0.103:8080/act/getLength","username="+username+"&beginTime="+beginTime+"&endTime="+endTime);
        return length;
    }


}
