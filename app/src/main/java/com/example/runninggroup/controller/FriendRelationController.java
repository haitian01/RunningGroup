package com.example.runninggroup.controller;

import android.content.res.Resources;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FriendRelationDao;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;

import java.util.List;
import java.util.TreeMap;

public class FriendRelationController {
    private FriendRelationControllerInterface mFriendRelationControllerInterface;
    public FriendRelationController(FriendRelationControllerInterface friendRelationControllerInterface) {
        mFriendRelationControllerInterface = friendRelationControllerInterface;
    }
    //获得好友的列表
    public void getFriends () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Cache.user != null) {
                    FriendRelation friendRelation = new FriendRelation();
                    User user = new User();
                    user.setId(Cache.user.getId());
                    friendRelation.setUser(user);
                    List<FriendRelation> friendRelationList = FriendRelationDao.getFriendRelation(friendRelation);
                    mFriendRelationControllerInterface.getFriendsBack(friendRelationList);
                }
            }
        }).start();
    }
    //获取指定的好友
    public void getFriendRelation (FriendRelation friendRelation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<FriendRelation> friendRelationList = FriendRelationDao.getFriendRelation(friendRelation);
                mFriendRelationControllerInterface.getFriendRelationBack(friendRelationList);
            }
        }).start();
    }
    //查询是否有某人的好友
    public void isMyFriend () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FriendRelation friendRelation = new FriendRelation();
                friendRelation.setUser(Cache.user);
                friendRelation.setFriend(Cache.friend);
                List<FriendRelation> friendRelationList = FriendRelationDao.getFriendRelation(friendRelation);
                mFriendRelationControllerInterface.isMyFriendBack(friendRelationList);

            }
        }).start();

    }

    /**
     * 删除好友
     * @param user_id
     * @param friend_id
     */
    public void deleteFriendRelation (int user_id, int friend_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = FriendRelationDao.deleteFriendRelation(user_id, friend_id);
                mFriendRelationControllerInterface.deleteFriendRelationBack(res);
            }
        }).start();
    }
    public void updateFriendRelation (FriendRelation friendRelation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = FriendRelationDao.updateFriendRelation(friendRelation);
                mFriendRelationControllerInterface.updateFriendRelationBack(res);
            }
        }).start();
    }








    public interface FriendRelationControllerInterface {
        //获得好友的列表
        default void getFriendsBack (List<FriendRelation> friendRelationList) {}
        //查询是否有某人的好友
        default void isMyFriendBack (List<FriendRelation> friendRelationList) {}
        //删除好友
        default void deleteFriendRelationBack (boolean res) {};

        default void updateFriendRelationBack(boolean res){};

        default void getFriendRelationBack(List<FriendRelation> friendRelationList){};

    }
}
