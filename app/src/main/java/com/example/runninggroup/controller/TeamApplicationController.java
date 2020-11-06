package com.example.runninggroup.controller;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.TeamApplicationDao;
import com.example.runninggroup.dao.TeamDao;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamApplication;
import com.example.runninggroup.pojo.User;

import java.util.List;

public class TeamApplicationController {
    private TeamApplicationControllerInterface mTeamApplicationControllerInterface;
    public TeamApplicationController(TeamApplicationControllerInterface teamApplicationControllerInterface) {
        mTeamApplicationControllerInterface = teamApplicationControllerInterface;
    }
    //申请加入跑团
    public void addTeamApplication (String applicationMsg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TeamApplication teamApplication = new TeamApplication();
                teamApplication.setApplicationMsg(applicationMsg);
                User user = new User();
                user.setId(Cache.user.getId());
                teamApplication.setUser(user);
                Team team = new Team();
                team.setId(Cache.team.getId());
                teamApplication.setTeam(team);
                boolean res = TeamApplicationDao.addTeamApplication(teamApplication);
                mTeamApplicationControllerInterface.addTeamApplicationBack(res);
            }
        }).start();
    }
    //活得所有的跑团申请
    public void getTeamApplication () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TeamApplication teamApplication = new TeamApplication();
                Team team = new Team();
                team.setId(Cache.user.getTeam().getId());
                teamApplication.setTeam(team);
                List<TeamApplication> teamApplications = TeamApplicationDao.getTeamApplication(teamApplication);
                mTeamApplicationControllerInterface.getTeamApplicationBack(teamApplications);
            }
        }).start();

    }
    //处理跑团申请
    public void updateTeamApplication (int id, int state, int user_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TeamApplication teamApplication = new TeamApplication();
                teamApplication.setId(id);
                teamApplication.setState(state);
                User user = new User();
                user.setId(user_id);
                Team team = new Team();
                team.setId(Cache.user.getTeam().getId());
                teamApplication.setUser(user);
                teamApplication.setTeam(team);
                boolean res = TeamApplicationDao.updateTeamApplication(teamApplication);
                mTeamApplicationControllerInterface.updateTeamApplicationBack(res);
            }
        }).start();

    }








    public interface TeamApplicationControllerInterface {
      default void addTeamApplicationBack (boolean res){};
      default void getTeamApplicationBack (List<TeamApplication> teamApplications){};
      default void updateTeamApplicationBack (boolean res){};
    }
}
