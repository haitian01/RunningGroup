package com.example.runninggroup.model;

import android.util.JsonWriter;

import com.example.runninggroup.request.PostRequest;

public class DaoUser {

    private DaoUser(){};
//    private static class Inner{
//        private static DaoUser daoUser = new DaoUser();
//    }
//    public static DaoUser getInstance(){
//        return Inner.daoUser;
//    }
    public static String isLoad(String username,String password){
      return PostRequest.userRequest("http://10.0.2.2:8080/user/load","username="+username+"&password="+password);
    }
    public static String Register(String username,String password){
        return PostRequest.userRequest("http://10.0.2.2:8080/user/register","username="+username+"&password="+password);
    }
}
