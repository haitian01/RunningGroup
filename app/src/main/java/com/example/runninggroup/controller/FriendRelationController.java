package com.example.runninggroup.controller;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FriendRelationDao;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;

import java.util.List;

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
                FriendRelation friendRelation = new FriendRelation();
                friendRelation.setUser(Cache.user);
                List<FriendRelation> friendRelationList = FriendRelationDao.getFriendRelation(friendRelation);
                mFriendRelationControllerInterface.getFriendsBack(friendRelationList);
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








    public interface FriendRelationControllerInterface {
        //获得好友的列表
        default void getFriendsBack (List<FriendRelation> friendRelationList) {}
        //查询是否有某人的好友
        default void isMyFriendBack (List<FriendRelation> friendRelationList) {}

    }
}
