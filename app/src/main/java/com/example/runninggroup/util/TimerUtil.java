package com.example.runninggroup.util;

import android.util.Log;

public class TimerUtil extends Thread{
    private long second = 0;
    boolean running = true;
    @Override
    public void run() {
        while(running){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            second++;
            System.out.println(("时间：" + getCurrentTime()));
        }
    }


    public String getCurrentTime(){
        long hour = second/(60*60);
        long minute = (second - hour*60*60)/60;
        long sec = second - hour*60*60 - minute*60;
        return append(hour)+":"+append(minute)+":"+append(sec);
    }
    public String append(long time){
        if(time<10){return "0"+time;}
        else return ""+time;
    }

    public void stopThread(){
        running = false;
    }


}
