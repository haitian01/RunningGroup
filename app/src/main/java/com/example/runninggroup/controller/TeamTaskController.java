package com.example.runninggroup.controller;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.TeamTaskDao;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamTask;
import com.example.runninggroup.pojo.User;

import java.util.List;

public class TeamTaskController {
     private TeamTaskControllerInterface mTeamTaskControllerInterface;
     public TeamTaskController (TeamTaskControllerInterface teamTaskControllerInterface) {
         mTeamTaskControllerInterface = teamTaskControllerInterface;
     }

     public void addTeamTask (String msg) {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 TeamTask teamTask = new TeamTask();
                 Team team = new Team();
                 User user = new User();
                 team.setId(Cache.user.getTeam().getId());
                 user.setId(Cache.user.getId());
                 teamTask.setTeam(team);
                 teamTask.setUser(user);
                 teamTask.setTaskMsg(msg);
                 boolean res = TeamTaskDao.addTeamTask(teamTask);
                 mTeamTaskControllerInterface.addTeamTaskBack(res);
             }
         }).start();
     }
    public void deleteTeamTask (TeamTask teamTask) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = TeamTaskDao.deleteTeamTask(teamTask);
                mTeamTaskControllerInterface.deleteTeamTaskBack(res);
            }
        }).start();
    }
    public void getTeamTask (TeamTask teamTask) {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 List<TeamTask> teamTaskList = TeamTaskDao.getTeamTask(teamTask);
                 mTeamTaskControllerInterface.getTeamTaskBack(teamTaskList);
             }
         }).start();
    }



    public interface TeamTaskControllerInterface {
         default void addTeamTaskBack (boolean res) {}
         default void deleteTeamTaskBack (boolean res) {}
         default void getTeamTaskBack (List<TeamTask> teamTaskList) {}


    }
}
