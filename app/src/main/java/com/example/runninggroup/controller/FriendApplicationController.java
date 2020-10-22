package com.example.runninggroup.controller;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FriendApplicationDao;
import com.example.runninggroup.dao.FriendRelationDao;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.FriendRelation;

import java.util.List;

public class FriendApplicationController {
    private FriendApplicationControllerInterface mFriendApplicationControllerInterface;
    public FriendApplicationController(FriendApplicationControllerInterface friendApplicationControllerInterface) {
        mFriendApplicationControllerInterface = friendApplicationControllerInterface;
    }


    //发送好友申请
    public void sendFriendApplication (String applicationMsg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FriendApplication friendApplication = new FriendApplication();
                friendApplication.setTo(Cache.friend);
                friendApplication.setFrom(Cache.user);
                friendApplication.setState(1);
                friendApplication.setApplicationMsg(applicationMsg);
                mFriendApplicationControllerInterface.sendFriendApplicationBack(FriendApplicationDao.startFriendApplication(friendApplication));
            }
        }).start();
    }

    //查询所有的申请记录
    public void getApplication () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FriendApplication friendApplication = new FriendApplication();
                friendApplication.setUser(Cache.user);
                List<FriendApplication> friendApplicationList = FriendApplicationDao.getFriendApplication(friendApplication);
                mFriendApplicationControllerInterface.getApplicationBack(friendApplicationList);
            }
        }).start();
    }





    public interface FriendApplicationControllerInterface {
        //发送申请
        default void sendFriendApplicationBack(boolean res) {}
        //查询所有申请记录
        default void getApplicationBack(List<FriendApplication> friendApplicationList) {}

    }
}
