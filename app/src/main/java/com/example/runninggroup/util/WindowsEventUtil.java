package com.example.runninggroup.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class WindowsEventUtil {
    /**
     * 键盘弹出
     * @param context
     * @param view
     */
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null){
            imm.showSoftInput(view, 0);
            // imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT); // 或者第二个参数传InputMethodManager.SHOW_IMPLICIT
        }
    }

    /**
     * 键盘收起
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            // imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY); // 或者第二个参数传InputMethodManager.HIDE_IMPLICIT_ONLY
        }
    }

    public static void systemBack () {
        new Thread () {
            public void run () {
                try {
                    Instrumentation inst= new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent. KEYCODE_BACK);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
