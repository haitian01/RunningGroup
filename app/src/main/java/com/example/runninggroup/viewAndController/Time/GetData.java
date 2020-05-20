package com.example.runninggroup.viewAndController.Time;

import com.example.runninggroup.viewAndController.Time.GetTime;

public class GetData {
    //根据时间戳获取跑步数据
    public Object[] getRunData(){
        Object[] runData = new Object[7];
        String today = GetTime.getWeekByTimeStamp(System.currentTimeMillis());
        if (today == "星期一"){
            runData = new Object[]{
                    0,0,0,0,0,0,0
            };
        }else if (today == "星期二"){
            runData = new Object[]{
                    0,0,0,0,0,0,0
            };
        }else if (today == "星期三"){
            runData = new Object[]{
                    0,0,0,0,0,0,0
            };
        }else if (today == "星期四"){
            runData = new Object[]{
                    0,0,0,0,0,0,0
            };
        }else if (today == "星期五"){
            runData = new Object[]{
                    0,0,0,0,0,0,0
            };
        }else if (today == "星期六"){
            runData = new Object[]{
                    0,0,0,0,0,0,0
            };
        }else if (today == "星期日"){
            runData = new Object[]{
                    0,0,0,0,0,0,0
            };
        }
        return runData;
    }
    //根据时间戳获取跑团数据
    public Object[] getGroud(){
        Object[] groudData = new Object[5];
        int month = GetTime.getMonthByTimeStamp(System.currentTimeMillis());
        if (month == 1){
            //今年1月往回到去年9月的数据
            groudData = new Object[]{
                    5,3,2,1,0
            };
        }else if (month == 2){
            groudData = new Object[]{
                    "Oct","Nov","Dec","Mon","Jan"
            };
        }
        else if (month == 3){
            groudData = new Object[]{
                    "Nov","Dec","Mon","Jan","Mar"
            };
        }else if (month == 4){
            groudData = new Object[]{
                    "Dec","Mon","Jan","Mar","Apr"
            };
        }else if (month == 5){
            groudData = new Object[]{
                    "Mon","Jan","Mar","Apr","May"
            };
        }else if (month == 6){
            groudData = new Object[]{
                    "Jan","Mar","Apr","May","Jun"
            };
        }else if (month == 7){
            groudData = new Object[]{
                    "Mar","Apr","May","Jun","Jul"
            };
        }else if (month == 8){
            groudData = new Object[]{
                    "Apr","May","Jun","Jul","Aug"
            };
        }else if (month == 9){
            groudData = new Object[]{
                    "May","Jun","Jul","Aug","Sep"
            };
        }else if (month == 10){
            groudData = new Object[]{
                    "Jun","Jul","Aug","Sep","Oct"
            };
        }else if (month == 11){
            groudData = new Object[]{
                    "Jul","Aug","Sep","Oct","Nov"
            };
        }else if (month == 12){
            groudData = new Object[]{
                    "Aug","Sep","Oct","Nov","Dec"
            };
        }
        return groudData;
    }

    //Echart图，返回从现在往前的五个月份
    public static Object[] getGroudMonth(){
        Object[] Month = new Object[5];
        int month = GetTime.getMonthByTimeStamp(System.currentTimeMillis());
        if (month == 1){
            Month = new Object[]{
                    "Sep","Oct","Nov","Dec","Jan"
            };
        }else if (month == 2){
            Month = new Object[]{
                    "Oct","Nov","Dec","Jan","Feb"
            };
        }
        else if (month == 3){
            Month = new Object[]{
                    "Nov","Dec","Jan","Feb","Mar"
            };
        }else if (month == 4){
            Month = new Object[]{
                    "Dec","Jan","Feb","Mar","Apr"
            };
        }else if (month == 5){
            Month = new Object[]{
                    "Jan","Feb","Mar","Apr","May"
            };
        }else if (month == 6){
            Month = new Object[]{
                    "Feb","Mar","Apr","May","Jun"
            };
        }else if (month == 7){
            Month = new Object[]{
                    "Mar","Apr","May","Jun","Jul"
            };
        }else if (month == 8){
            Month = new Object[]{
                    "Apr","May","Jun","Jul","Aug"
            };
        }else if (month == 9){
            Month = new Object[]{
                    "May","Jun","Jul","Aug","Sep"
            };
        }else if (month == 10){
            Month = new Object[]{
                    "Jun","Jul","Aug","Sep","Oct"
            };
        }else if (month == 11){
            Month = new Object[]{
                    "Jul","Aug","Sep","Oct","Nov"
            };
        }else if (month == 12){
            Month = new Object[]{
                    "Aug","Sep","Oct","Nov","Dec"
            };
        }
        return Month;
    }
}
