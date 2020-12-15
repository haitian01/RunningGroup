package com.example.runninggroup.controller;

import com.example.runninggroup.dao.ActDao;
import com.example.runninggroup.pojo.Act;

import java.util.List;

public class ActController {
    private ActControllerInterface mActControllerInterface;
    public ActController (ActControllerInterface actControllerInterface) {
        mActControllerInterface = actControllerInterface;
    }

    /**
     * 获取活动记录
     * @param act
     */
    public void selectAct (Act act) {
       new Thread(new Runnable() {
           @Override
           public void run() {
               List<Act> acts = ActDao.selectAct(act);
               if (mActControllerInterface != null) mActControllerInterface.selectActBack(acts);
           }
       }).start();
    }


    /**
     * 插入一条活动
     */
    public void addAct (Act act) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = ActDao.addAct(act);
                mActControllerInterface.addActBack(res);
            }
        }).start();
    }


    /**
     * 更新一条打卡记录（分享状态更新）
     */

    public void updateAct (Act act) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = ActDao.updateAct(act);
                mActControllerInterface.updateActBack(res);
            }
        }).start();
    }

    /**
     * 删除Act
     */
    public void deleteAct (Act act) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = ActDao.deleteAct(act);
                mActControllerInterface.deleteActBack(res);
            }
        }).start();
    }

    public interface ActControllerInterface {
        default void selectActBack (List<Act> acts){};
        default void addActBack (boolean res){};
        default void updateActBack (boolean res){};
        default void deleteActBack (boolean res){};
    }
}
