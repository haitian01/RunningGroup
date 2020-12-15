package com.example.runninggroup.controller;


import com.example.runninggroup.dao.TeamNoticeDao;
import com.example.runninggroup.pojo.TeamNotice;

import java.util.List;

public class TeamNoticeController {
    private TeamNoticeControllerInterface mTeamNoticeControllerInterface;
    public TeamNoticeController (TeamNoticeControllerInterface teamNoticeControllerInterface) {
        mTeamNoticeControllerInterface = teamNoticeControllerInterface;
    }

    /**
     * 添加公告
     * @param teamNotice
     */
    public void addTeamNotice (TeamNotice teamNotice) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res = TeamNoticeDao.addTeamNotice(teamNotice);
                if (mTeamNoticeControllerInterface != null) mTeamNoticeControllerInterface.addTeamNoticeBack(res);
            }
        }).start();
    }

    /**
     * 拉去所有公告
     * @param teamNotice
     */

    public void getTeamNotice (TeamNotice teamNotice) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TeamNotice> teamNoticeList = TeamNoticeDao.getTeamNotice(teamNotice);
                if (mTeamNoticeControllerInterface != null) mTeamNoticeControllerInterface.getTeamNoticeBack(teamNoticeList);
            }
        }).start();
    }

    /**
     * 拉去指定条数公告
     * @param team_id
     * @param limit
     */

    public void getTeamNoticeLimit (int team_id, int limit) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TeamNotice> teamNoticeList = TeamNoticeDao.getTeamNoticeLimit(team_id, limit);
                if (mTeamNoticeControllerInterface != null) mTeamNoticeControllerInterface.getTeamNoticeLimitBack(teamNoticeList);
            }
        }).start();
    }

    public interface TeamNoticeControllerInterface {
        default void addTeamNoticeBack (String res){};
        default void getTeamNoticeBack (List<TeamNotice> teamNoticeList){};
        default void getTeamNoticeLimitBack (List<TeamNotice> teamNoticeList){};
    }
}
