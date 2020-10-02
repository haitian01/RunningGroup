package com.example.runninggroup.controller;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FriendRelationDao;
import com.example.runninggroup.pojo.FriendRelation;

import java.util.List;

public class FriendRelationController {
    private FriendRelationControllerInterface mFriendRelationControllerInterface;
    public FriendRelationController(FriendRelationControllerInterface friendRelationControllerInterface) {
        mFriendRelationControllerInterface = friendRelationControllerInterface;
    }
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





    public interface FriendRelationControllerInterface {
        default void getFriendsBack (List<FriendRelation> friendRelationList) {}
    }
}
