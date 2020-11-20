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


    }

    private void initView() {
        applicationList = findViewById(R.id.application);
        backImg = findViewById(R.id.back);
        clearTxt = findViewById(R.id.clear);
        applicationList.setAdapter(new TeamApplicationAdapter(getLayoutInflater(), mTeamApplications, this));
        mTeamApplicationController.getTeamApplication();

    }

    @Override
    public void getTeamApplicationBack(List<TeamApplication> teamApplications) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (teamApplications == null) Toast.makeText(TeamApplicationActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else  {
                    mTeamApplications = teamApplications;
                    applicationList.setAdapter(new TeamApplicationAdapter(getLayoutInflater(), mTeamApplications, TeamApplicationActivity.this));
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
