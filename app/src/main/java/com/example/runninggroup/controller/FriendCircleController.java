package com.example.runninggroup.controller;

import android.content.res.Resources;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FriendCircleDao;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.viewAndController.adapter.FriendCircleAdapter;

import java.util.List;

public class FriendCircleController {
    FriendCircleControllerInterface mFriendCircleControllerInterface;
    public FriendCircleController (FriendCircleControllerInterface friendCircleControllerInterface) {
        mFriendCircleControllerInterface = friendCircleControllerInterface;
    }

    public void addFriendCircle (String circleMsg, Integer imgNum) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FriendCircle friendCircle = new FriendCircle();
                User user = new User();
                user.setId(Cache.user.getId());
                friendCircle.setCircleMsg(circleMsg);
                friendCircle.setUser(user);
                friendCircle.setImgNum(imgNum);
                String res = FriendCircleDao.addFriendCircle(friendCircle);
                mFriendCircleControllerInterface.addFriendCircleBack(res);
            }
        }).start();
    }

    //第一次加载
    public void getFriendCircleFirst () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<FriendCircle> friendCircleList = FriendCircleDao.getFriendCircleFirst();
                mFriendCircleControllerInterface.getFriendCircleFirstBack(friendCircleList);
            }
        }).start();
    }
    //范围加载
    public void getFriendCircle (int id, FriendCircleAdapter.LoadHolder loadHolder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<FriendCircle> friendCircleList = FriendCircleDao.getFriendCircle(id, 0);
                mFriendCircleControllerInterface.getFriendCircleBack(friendCircleList, loadHolder);
            }
        }).start();
    }

    //点赞
    public void updateZan (FriendCircleAdapter.InnerHolder innerHolder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = FriendCircleDao.updateZan(innerHolder.mFriendCircle.getId(), Cache.user.getId());
                mFriendCircleControllerInterface.updateZanBack(res, innerHolder);
            }
        }).start();
    }

    //删除动态
    public void deleteFriendCircle (FriendCircleAdapter.InnerHolder innerHolder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = FriendCircleDao.deleteFriendCircle(innerHolder.mFriendCircle);
                mFriendCircleControllerInterface.deleteFriendCircleBack(res);
            }
        }).start();
    }

    public interface FriendCircleControllerInterface {
        default void getFriendCircleFirstBack( List<FriendCircle> friendCircleList){};
        default void getFriendCircleBack( List<FriendCircle> friendCircleList, FriendCircleAdapter.LoadHolder loadHolder){};
        default void addFriendCircleBack (String res) {};
        default void updateZanBack (boolean res, FriendCircleAdapter.InnerHolder innerHolder){};
        default void deleteFriendCircleBack (boolean res){};
    }
}
