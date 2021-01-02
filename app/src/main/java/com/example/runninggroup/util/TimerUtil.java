package com.example.runninggroup.util;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.pojo.Act;

public class TimerUtil extends Thread{
    private long second;
    boolean running = true;
    private Activity mActivity;
    public TimerUtil (Activity activity, long second) {
        mActivity = activity;
        this.second = second;
    }
    @Override
    public void run() {
        while(running){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            second++;
            if (mActivity != null) {
                TextView timeTxt = (TextView)mActivity.findViewById(R.id.time);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTxt.setText(getCurrentTime());
                    }
                });
            }
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

    public long getSecond() {
        return second;
    }
}
