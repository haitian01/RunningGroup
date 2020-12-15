package com.example.runninggroup.viewAndController;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.TeamApplicationController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamApplication;
import com.example.runninggroup.viewAndController.adapter.FriendApplicationAdapter;
import com.example.runninggroup.viewAndController.adapter.TeamApplicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamApplicationActivity extends AppCompatActivity implements TeamApplicationController.TeamApplicationControllerInterface {
    private ListView applicationList;
    private TeamApplicationController mTeamApplicationController = new TeamApplicationController(this);
    private List<TeamApplication> mTeamApplications = new ArrayList<>();
    private ImageView backImg;
    private TextView clearTxt;
    private TeamApplicationAdapter mTeamApplicationAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_application);
        initView();
        initEvent();
    }

    private void initEvent() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamApplicationActivity.this, Manage.class);
                startActivity(intent);
            }
        });
        clearTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamApplication teamApplication = new TeamApplication();
                Team team = new Team();
                team.setId(Cache.user.getTeam().getId());
                teamApplication.setTeam(team);
                mTeamApplicationController.deleteTeamApplication(teamApplication);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTeamApplicationController.getTeamApplication();
            }
        });





    }

    private void initView() {
        applicationList = findViewById(R.id.application);
        backImg = findViewById(R.id.back);
        clearTxt = findViewById(R.id.clear);
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        mTeamApplicationAdapter = new TeamApplicationAdapter(getLayoutInflater(), mTeamApplications, this);
        /**
         * 设置同意or拒绝的点击事件
         */
        mTeamApplicationAdapter.setBtnOnClickListener(new TeamApplicationAdapter.BtnOnClickListener() {
            @Override
            public void acceptOnClick(TeamApplicationAdapter.ViewHolder viewHolder) {
                TeamApplication teamApplication = viewHolder.mTeamApplication;
                mTeamApplicationController.updateTeamApplication(teamApplication.getId(), 2, teamApplication.getUser().getId(), viewHolder);
            }

            @Override
            public void refuseOnClick(TeamApplicationAdapter.ViewHolder viewHolder) {
                TeamApplication teamApplication = viewHolder.mTeamApplication;
                mTeamApplicationController.updateTeamApplication(teamApplication.getId(), 3, teamApplication.getUser().getId(), viewHolder);
            }
        });
        applicationList.setAdapter(mTeamApplicationAdapter);
        mTeamApplicationController.getTeamApplication();

    }


    /**
     * 跑团申请列表点击同意or拒绝的回调
     * @param res
     * @param state
     * @param viewHolder
     */

    @Override
    public void updateTeamApplicationBack(boolean res, int state, TeamApplicationAdapter.ViewHolder viewHolder) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamApplicationActivity.this,  res ? "success" : "error", Toast.LENGTH_SHORT).show();
                if (res) {
                    viewHolder.acceptBtn.setVisibility(View.INVISIBLE);
                    viewHolder.refuseBtn.setVisibility(View.INVISIBLE);
                    viewHolder.stateText.setText(state == 2 ? "已同意" : "已拒绝");
                }
            }
        });
    }

    /**
     * 获取Application回调
     * @param teamApplications
     */
    @Override
    public void getTeamApplicationBack(List<TeamApplication> teamApplications) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (teamApplications == null) Toast.makeText(TeamApplicationActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else  {
                    mTeamApplications.clear();
                    mTeamApplications.addAll(teamApplications);
                    applicationList.setAdapter(mTeamApplicationAdapter);
                }
            }
        });
    }

    @Override
    public void deleteTeamApplicationBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamApplicationActivity.this, res ? "success" : "fail" , Toast.LENGTH_SHORT).show();
                if (res)  mTeamApplicationController.getTeamApplication();
            }
        });
    }
}
