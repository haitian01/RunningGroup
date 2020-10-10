package com.example.runninggroup.util;

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

    public static void main(String[] args) {
        System.out.println(getRegisterNum());
    }
}
