package com.example.runninggroup.viewAndController.TimeAndData;

import com.example.runninggroup.model.DaoAct;

public class GetData {
    public String username = null;
    //根据时间戳获取跑步数据
    public Object[] getRunData(){
        Object[] runData = new Object[7];
        String today = GetTime.getWeekByTimeStamp(System.currentTimeMillis());
        Long todayBegin = GetTime.getDayBegin();
        Long todayEnd = GetTime.getDayEnd();

        long data_1_1 = todayBegin - 1000*60*60*24;
        long data_1_2 = todayEnd - 1000*60*60*24;

        long data_2_1 = todayBegin - 1000*60*60*24 * 2;
        long data_2_2 = todayEnd - 1000*60*60*24 * 2;

        long data_3_1 = todayBegin - 1000*60*60*24 * 3;
        long data_3_2 = todayEnd - 1000*60*60*24 * 3;

        long data_4_1 = todayBegin - 1000*60*60*24 * 4;
        long data_4_2 = todayEnd - 1000*60*60*24 * 4;

        long data_5_1 = todayBegin - 1000*60*60*24 * 5;
        long data_5_2 = todayEnd - 1000*60*60*24 * 5;

        long data_6_1 = todayBegin - 1000*60*60*24 * 6;
        long data_6_2 = todayEnd - 1000*60*60*24 * 6;

        if (today == "星期一"){
            runData = new Object[]{
                    DaoAct.getLength(username,todayBegin,todayEnd),0,0,0,0,0,0
            };
        }else if (today == "星期二"){
            runData = new Object[]{
                    DaoAct.getLength(username,data_1_1,data_1_2), DaoAct.getLength(username,todayBegin,todayEnd),0,0,0,0,0
            };
        }else if (today == "星期三"){
            runData = new Object[]{
                    DaoAct.getLength(username,data_2_1,data_2_2),DaoAct.getLength(username,data_1_1,data_1_2), DaoAct.getLength(username,todayBegin,todayEnd),0,0,0,0
            };
        }else if (today == "星期四"){
            runData = new Object[]{
                    DaoAct.getLength(username,data_3_1,data_3_2),DaoAct.getLength(username,data_2_1,data_2_2),DaoAct.getLength(username,data_1_1,data_1_2), DaoAct.getLength(username,todayBegin,todayEnd),0,0,0
            };
        }else if (today == "星期五"){
            runData = new Object[]{
                    DaoAct.getLength(username,data_4_1,data_4_2),DaoAct.getLength(username,data_3_1,data_3_2),DaoAct.getLength(username,data_2_1,data_2_2),DaoAct.getLength(username,data_1_1,data_1_2), DaoAct.getLength(username,todayBegin,todayEnd),0,0
            };
        }else if (today == "星期六"){
            runData = new Object[]{
                    DaoAct.getLength(username,data_5_1,data_5_2),DaoAct.getLength(username,data_4_1,data_4_2),DaoAct.getLength(username,data_3_1,data_3_2),DaoAct.getLength(username,data_2_1,data_2_2),DaoAct.getLength(username,data_1_1,data_1_2), DaoAct.getLength(username,todayBegin,todayEnd),0
            };
        }else if (today == "星期日"){
            runData = new Object[]{
                    DaoAct.getLength(username,data_6_1,data_6_2),DaoAct.getLength(username,data_5_1,data_5_2),DaoAct.getLength(username,data_4_1,data_4_2),DaoAct.getLength(username,data_3_1,data_3_2),DaoAct.getLength(username,data_2_1,data_2_2),DaoAct.getLength(username,data_1_1,data_1_2), DaoAct.getLength(username,todayBegin,todayEnd)
            };
        }
        return runData;
    }

    //根据时间戳获取跑团数据
    public Object[] getGroud(){
        Object[] groudData = new Object[5];
        int month = GetTime.getMonthByTimeStamp(System.currentTimeMillis());
        //本月
        long monthBegin = GetTime.monthTimeInMillis();
        long monthEnd = GetTime.monthTimeInMillis_end() - 1000;
        //上个月
        long monthBegin_1 = GetTime.monthTimeInMillis_1();
        long monthEnd_1 = GetTime.monthTimeInMillis() - 1000;
        //上上个月
        long monthBegin_2 = GetTime.monthTimeInMillis_2();
        long monthEnd_2 = GetTime.monthTimeInMillis_1() - 1000;
        //上上上个月
        long monthBegin_3 = GetTime.monthTimeInMillis_3();
        long monthEnd_3 = GetTime.monthTimeInMillis_2() - 1000;
        //上上上上个月
        long monthBegin_4 = GetTime.monthTimeInMillis_4();
        long monthEnd_4 = GetTime.monthTimeInMillis_3() - 1000;

        if (month == 1){
            //今年1月往回到去年9月的数据
            groudData = new Object[]{
                    5,3,2,1,0
            };
            //今年2月往回到去年10月的数据
        }else if (month == 2){
            groudData = new Object[]{

            };
            //今年3月往回到去年11月的数据
        } else if (month == 3){
            groudData = new Object[]{

            };
            //今年4月往回到去年12月的数据
        }else if (month == 4){
            groudData = new Object[]{

            };
            //今年5月往回到今年1月的数据
        }else if (month == 5){
            groudData = new Object[]{

            };
            //今年6月往回到今年2月的数据
        }else if (month == 6){
            groudData = new Object[]{

            };
            //今年7月往回到今年3月的数据
        }else if (month == 7){
            groudData = new Object[]{

            };
            //今年8月往回到今年4月的数据
        }else if (month == 8){
            groudData = new Object[]{

            };
            //今年9月往回到今年5月的数据
        }else if (month == 9){
            groudData = new Object[]{

            };
            //今年10月往回到今年6月的数据
        }else if (month == 10){
            groudData = new Object[]{

            };
            //今年11月往回到今年7月的数据
        }else if (month == 11){
            groudData = new Object[]{

            };
            //今年12月往回到今年8月的数据
        }else if (month == 12){
            groudData = new Object[]{

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
