package com.example.runninggroup.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringUtil {

    public static boolean isStringNull (String str) {
        if (str == null) return true;
        if ("".equals(str)) return true;
        return false;
    }
    public static String getRegisterNum () {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            if (i == 0) stringBuilder.append(1 + random.nextInt(10));
            else stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    /**
     * 根据long返回时间
     * @return
     */
    public static String getTime (Long millisecond) {
        long allSecond = millisecond / 1000;
        long hour = allSecond / 3600;
        long minute = (allSecond - hour * 3600) / 60;
        long second = allSecond - hour * 3600 - minute * 60;

        return getTimeHelper(hour) + ":" + getTimeHelper(minute) + ":" + getTimeHelper(second);

    }

    /**
     *
     * @param millisecond 跑步时间的毫秒数
     * @param runLen 跑步里程 单位公里
     * @return
     */
    public static String getSpeed (Long millisecond, Double runLen) {
        long allSecond = (long) (millisecond / runLen / 1000);
        long hour = allSecond / 3600;
        long minute = (allSecond - hour * 3600) / 60;
        long second = allSecond - hour * 3600 - minute * 60;
        return getTimeHelper(minute) + "\'" + getTimeHelper(second) + "\"";

    }

    /**
     * 给定原始字符串和目标字符串，会返回一个List集合，里面每个元素格式：index1,index2。表示下标位置为index1到index2
     * @param word
     * @param str
     * @return
     */
    public static List<String> getWordPosition (String word, String str) {
        List<String> res = new ArrayList<>();
        if (! str.contains(word)) return res;
        else {
            String s = str;
            int index1 = 0;
            int index2 = -1;
            while (s.contains(word)) {
                int temp = index2;
                index1 = index2 + s.indexOf(word) + 1;
                index2 = index1 + word.length() - 1;
                res.add(index1 + "," + index2);
                s = s.substring(index2 - temp );
            }
            return res;
        }
    }
    // index1  0     4
    // index2   0   4

    // 0 0
    //

    public static void main(String[] args) {
        List<String> stringList = getWordPosition("马保", "马保国（马保国）");
        for (String s : stringList) {
            System.out.println(s);
        }
    }


    private static String getTimeHelper (long time) {
        if (time >= 10) return time + "";
        else return 0 + "" + time;
    }

}
