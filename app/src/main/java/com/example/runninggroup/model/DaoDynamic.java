package com.example.runninggroup.model;

import com.example.runninggroup.request.PostRequest;

public class DaoDynamic {

    private DaoDynamic(){};


    //插入一条动态
    public static String writeDynamic(String username,String dynamic_msg){
        String result = PostRequest.postRequest("http://192.168.0.104:8080/dynamic/writeDynamic","username="+username+"&dynamic_msg="+dynamic_msg+"&dynamic_time="+System.currentTimeMillis());
        return result;
    }


}
