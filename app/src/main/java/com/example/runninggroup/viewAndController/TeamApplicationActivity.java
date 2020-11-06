package com.example.runninggroup.viewAndController;

import android.app.Application;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.TeamApplicationController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.TeamApplication;
import com.example.runninggroup.viewAndController.adapter.FriendApplicationAdapter;
import com.example.runninggroup.viewAndController.adapter.TeamApplicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamApplicationActivity extends AppCompatActivity implements TeamApplicationController.TeamApplicationControllerInterface {
    private ListView applicationList;
    private TeamApplicationController mTeamApplicationController = new TeamApplicationController(this);
    private List<TeamApplication> mTeamApplications = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_application);
        initView();
        initEvent();
    }

    private void initEvent() {


    }

    private void initView() {
        applicationList = findViewById(R.id.application);
        applicationList.setAdapter(new TeamApplicationAdapter(getLayoutInflater(), mTeamApplications, this, this));
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
                    applicationList.setAdapter(new TeamApplicationAdapter(getLayoutInflater(), mTeamApplications, TeamApplicationActivity.this, TeamApplicationActivity.this));
                }
            }
        });
    }
}
