package com.example.runninggroup.util;


public class CharacterUtil {
    public static String toUNICODE(String s) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<s.length();i++) {
        if(s.charAt(i)<=256) {
        sb.append("u00");
        }
        else{ sb.append("u");
        }
        sb.append(Integer.toHexString(s.charAt(i)));
        }
        return sb.toString();
    }

    public static String getTimeLength(long beginTime,long endTime){
        long length = endTime - beginTime;
        long hour = length/(60*60*1000);
        long minute = (length - hour*(60*60*1000))/(60*1000);
        return hour + "h"+ " " + minute + "min";
    }




}
