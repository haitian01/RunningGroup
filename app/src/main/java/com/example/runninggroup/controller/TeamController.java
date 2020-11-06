package com.example.runninggroup.controller;

import android.graphics.drawable.Drawable;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.dao.TeamDao;
import com.example.runninggroup.dao.UserDao;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StringUtil;

import java.io.File;
import java.util.List;

public class TeamController {
    private TeamControllerInterface mTeamControllerInterface;
    public TeamController(TeamControllerInterface teamControllerInterface) {
        mTeamControllerInterface = teamControllerInterface;
    }
    public void getTeam (Team team) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Team> team1 = TeamDao.getTeam(team);
                mTeamControllerInterface.getTeamBack(team1);
            }
        }).start();
    }
    public void addTeam (Team team) {
       new Thread(new Runnable() {
           @Override
           public void run() {
               String num = "1" + Cache.user.getRegisterNum();
               team.setRegisterNum(num);
               team.setUser(Cache.user);
               User user = TeamDao.buildTeam(team);

               if (user != null) {
                   Cache.user = user;
                   mTeamControllerInterface.buildTeamBack(true);
               }
               else mTeamControllerInterface.buildTeamBack(false);

           }
       }).start();
    }
    public void updateTeamSlogan (Team team) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                team.setId(Cache.user.getTeam().getId());
                boolean res = TeamDao.updateTeam(team);
                if (res) Cache.user.getTeam().setTeamSlogan(team.getTeamSlogan());
                mTeamControllerInterface.updateTeamSloganBack(res);
            }
        }).start();
    }








    public interface TeamControllerInterface {
       default void getTeamBack (List<Team> teams){}
        default void buildTeamBack (boolean res){}
        default void updateTeamSloganBack (boolean res){};
    }
}
