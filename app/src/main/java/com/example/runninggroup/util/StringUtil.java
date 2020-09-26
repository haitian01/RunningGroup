package com.example.runninggroup.util;

public class StringUtil {

    public static boolean isStringNull (String str) {
        if (str == null) return true;
        if ("".equals(str)) return true;
        return false;
    }
}
