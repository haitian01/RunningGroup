package com.example.runninggroup.viewAndController.TimeAndData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//获取本地时间、系统时间为基准
public class GetTime {

    //获取当天的开始时间
    public static Long getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }
    //获取当天的结束时间
    public static Long getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime().getTime();
    }
    //获取当月开始的时间戳
    public static Long monthTimeInMillis() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }

    public static Long monthTimeInMillis_end() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }

    //获取近五个月的起始时间戳
    public static Long monthTimeInMillis_1() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }
    public static Long monthTimeInMillis_2() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }
    public static Long monthTimeInMillis_3() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }
    public static Long monthTimeInMillis_4() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }


    //将给定时间戳转为字符串形式（2020年04月01日 12时00分00秒）
    public static String timeStampToDate(long timeStamp){
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
    //将给定时间戳转为年整数形式（2020）
    public static int getYearByTimeStamp(long timeStamp){
        String date = timeStampToDate(timeStamp);
        String year = date.substring(0,4);
        return Integer.parseInt(year);
    }
    //将给定时间戳转为月整数形式（4）
    public static int getMonthByTimeStamp(long timeStamp){
        String date = timeStampToDate(timeStamp);
        String month = date.substring(5,7);
        return Integer.parseInt(month);
    }
    //将给定时间戳转为日整数形式（1）
    public static int getDayByTimeStamp(long timeStamp){
        String date = timeStampToDate(timeStamp);
        String day = date.substring(8,10);
        return Integer.parseInt(day);
    }
    //将给定时间戳转为小时整数形式（12时）
    public static int getHourByTimeStamp(long timeStamp){
        String date = timeStampToDate(timeStamp);
        String hour = date.substring(12,14);
        return Integer.parseInt(hour);
    }
    //将给定时间戳转为分钟整数形式（00）
    public static int getMinuteByTimeStamp(long timeStamp){
        String date = timeStampToDate(timeStamp);
        String minute = date.substring(15,17);
        return Integer.parseInt(minute);
    }
    //将给定时间戳转为秒整数形式（00）
    public static int getSecondByTimeStamp(long timeStamp){
        String date = timeStampToDate(timeStamp);
        String second = date.substring(18,20);
        return Integer.parseInt(second);
    }
    //根据给定时间戳判定今天星期几
    public static String getWeekByTimeStamp(long timeStamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        int week = 0;
        week = calendar.get(Calendar.DAY_OF_WEEK);
        String Week = null;
        if (week == 1){
            Week = "星期日";
        }
        else  if (week == 2){
            Week = "星期一";
        }
        else if (week == 3){
            Week = "星期二";
        }
        else  if (week == 4){
            Week = "星期三";
        }
        else if (week == 5){
            Week = "星期四";
        }
        else if (week == 6){
            Week = "星期五";
        }
        else if (week == 7){
            Week = "星期六";
        }
        return Week;
    }

//高岳要的方法
    public static String getBeginTime(String type){
        int year = getYearByTimeStamp(System.currentTimeMillis());
        int month = getMonthByTimeStamp(System.currentTimeMillis());
        int day = getDayByTimeStamp(System.currentTimeMillis());
        String week = getWeekByTimeStamp(System.currentTimeMillis());
        if (type == "本日"){
            String dateString = year + "-" + month + "-" + day;
            return dateString;
        }
        if (type == "本周") {
            if (week == "星期一"){
                String dateString = year + "-" + month + "-" + day;
                return dateString;
            }
            if (week == "星期二"){
                long timeStamp = System.currentTimeMillis() - 86400000;
                int year_new = getYearByTimeStamp(timeStamp);
                int month_new = getMonthByTimeStamp(timeStamp);
                int day_new = getDayByTimeStamp(timeStamp);
                String dateString = year_new + "-" + month_new + "-" + day_new;
                return dateString;
            }
            if (week == "星期三"){
                long timeStamp = System.currentTimeMillis() - 2*86400000;
                int year_new = getYearByTimeStamp(timeStamp);
                int month_new = getMonthByTimeStamp(timeStamp);
                int day_new = getDayByTimeStamp(timeStamp);
                String dateString = year_new + "-" + month_new + "-" + day_new;
                return dateString;
            }
            if (week == "星期四"){
                long timeStamp = System.currentTimeMillis() - 3*86400000;
                int year_new = getYearByTimeStamp(timeStamp);
                int month_new = getMonthByTimeStamp(timeStamp);
                int day_new = getDayByTimeStamp(timeStamp);
                String dateString = year_new + "-" + month_new + "-" + day_new;
                return dateString;
            }
            if (week == "星期五"){
                long timeStamp = System.currentTimeMillis() - 4*86400000;
                int year_new = getYearByTimeStamp(timeStamp);
                int month_new = getMonthByTimeStamp(timeStamp);
                int day_new = getDayByTimeStamp(timeStamp);
                String dateString = year_new + "-" + month_new + "-" + day_new;
                return dateString;
            }
            if (week == "星期六"){
                long timeStamp = System.currentTimeMillis() - 5*86400000;
                int year_new = getYearByTimeStamp(timeStamp);
                int month_new = getMonthByTimeStamp(timeStamp);
                int day_new = getDayByTimeStamp(timeStamp);
                String dateString = year_new + "-" + month_new + "-" + day_new;
                return dateString;
            }
            if (week == "星期日"){
                long timeStamp = System.currentTimeMillis() - 6*86400000;
                int year_new = getYearByTimeStamp(timeStamp);
                int month_new = getMonthByTimeStamp(timeStamp);
                int day_new = getDayByTimeStamp(timeStamp);
                String dateString = year_new + "-" + month_new + "-" + day_new;
                return dateString;
            }
        }
        if (type == "本月"){
            String dateString = year + "-" + month + "-" + 1;
            return dateString;
        }
        else {
            return null;
        }
    }


//刘齐瓒的方法
    public static String[][] transString(long timeStamp){
        String[][] array = new String[1][2];
        int year = getYearByTimeStamp(timeStamp);
        int month = getMonthByTimeStamp(timeStamp);
        int day = getDayByTimeStamp(timeStamp);
        int hour = getHourByTimeStamp(timeStamp);
        int minute = getMinuteByTimeStamp(timeStamp);
        int second = getSecondByTimeStamp(timeStamp);
        array[0][0] = year + "年" + month + "月" + day + "日";
        array[0][1] = hour + "时" + minute + "分" + second + "秒";
        return array;
    }
    public static String transString1(long continueTime){
        long allseconds = continueTime / 1000;
        long lastseconds = allseconds % 3600;
        long hour = allseconds / 3600;
        long minute = lastseconds / 60;
        long second = lastseconds % 60;
        String dataString = hour + "时" + minute + "分" + second + "秒";
        return dataString;
    }
    public static float transString2(long continueTime){
        long allseconds = continueTime/1000;
        long lastseconds = allseconds % 3600;
        long hour = allseconds/3600;
        long minute = lastseconds / 60;
        long second = lastseconds % 60;
        float intmin =(float) minute/60;
        float intsec = (float) second/3600;
        float inth = (float) hour;
        float dataInt = intmin+intsec+inth;
        return dataInt;
    }

//自己乱写的
    //获取当前时间并显示为字符串形式 12：24：23
    public static String getNowTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date curData = new Date(System.currentTimeMillis());
        String string = format.format(curData);
        return string;
    }

    //获取起始时间并返回长整型（long）
    public static long getStartTime(){
        long startTime = System.currentTimeMillis();
        return startTime;
    }

    //将获取的长整型起始时间与当前的时间进行计算并返回字符串型持续时间 12：24：23
    public static String countTime(long startTime){
        SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
        long endTime = System.currentTimeMillis();
//        String contineTime = format.format(startTime - endTime);
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // long ns = 1000;
        // 获得两个时间的秒时间差异
        long diff = (endTime) - (startTime);
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        String res = "";
        res += ""+hour + "时"+min + "分"+sec+"秒";

        return res;
//        return contineTime;
    }

    //将字符串转换为时间戳，pattern为日期格式
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    //Calendar应用实例
//    public static void main(String[] args) {
//        // 其日历字段已由当前日期和时间初始化：
//        Calendar rightNow = Calendar.getInstance(); // 子类对象
//        // 获取年
//        int year = rightNow.get(Calendar.YEAR);
//        // 获取月
//        int month = rightNow.get(Calendar.MONTH);
//        // 获取日
//        int date = rightNow.get(Calendar.DATE);
//        // 获取几点
//        int hour=rightNow.get(Calendar.HOUR_OF_DAY);
//        // 获取上午下午
//        int moa=rightNow.get(Calendar.AM_PM);
//        if(moa==1)
//            System.out.println("下午");
//        else
//            System.out.println("上午");
//        System.out.println(year + "年" + (month + 1) + "月" + date + "日"+hour+"时");
//        rightNow.add(Calendar.YEAR,5);
//        rightNow.add(Calendar.DATE, -10);
//        int year1 = rightNow.get(Calendar.YEAR);
//        int date1 = rightNow.get(Calendar.DATE);
//        System.out.println(year1 + "年" + (month + 1) + "月" + date1 + "日"+hour+"时");
//    }
}



