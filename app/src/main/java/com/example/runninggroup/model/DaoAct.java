package com.example.runninggroup.model;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.request.GetRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.viewAndController.helper.GroupHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;
import com.example.runninggroup.viewAndController.helper.PersonalCardHelper;

import java.util.ArrayList;
import java.util.Collections;
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
    public static boolean insertAct(String username,long beginTime,long endTime,long length,double score,String act_type,String begin_place,String end_place){
        String result =  PostRequest.postRequest("http://39.97.66.19:8080/act/insertAct","username="+username+"&beginTime="+beginTime+"&endTime="+endTime+"&length="+length+"&score="+score+"&act_type="+act_type+"&begin_place="+begin_place+"&end_place="+end_place);
        if (result == null) return false;
        else {
            if("SUCCESS".equals(result)){return true;}
            return false;
        }
    }
    //拿到所有的打卡记录
    public static List<PersonalCardHelper> queryCard(String username){
        String result =  PostRequest.postRequest("http://39.97.66.19:8080/act/queryCard","username="+username);
        List<PersonalCardHelper> list = JSONObject.parseArray(result,PersonalCardHelper.class);
        if(list == null){
            return new ArrayList<PersonalCardHelper>();
        }
        Collections.reverse(list);
        return list;
    }


    //拿到某时间段分数
    public static String getScore(String username,long beginTime,long endTime){
        String score =  PostRequest.postRequest("http://39.97.66.19:8080/act/getScore","username="+username+"&beginTime="+beginTime+"&endTime="+endTime);
        if (score == null) return "0";
        return score;
    }





}
