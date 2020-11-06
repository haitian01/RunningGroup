package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.controller.TeamController;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.viewAndController.fragment.FragmentGroup;

public class GroupBuild extends AppCompatActivity implements View.OnClickListener, TeamController.TeamControllerInterface {
    EditText groupNameEdt,sloganEdt,campusEdt,collegeEdt;

    Button build;
    String username;
    private TeamController mTeamController = new TeamController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuild);
        initView();
        initEvent();
    }

    private void initView() {
        build = findViewById(R.id.build);
        groupNameEdt = findViewById(R.id.groupname);
        sloganEdt = findViewById(R.id.slogan);
        campusEdt = findViewById(R.id.campus);
        collegeEdt = findViewById(R.id.college);
    }
    private void initEvent() {
        build.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.build:
                String teamName = groupNameEdt.getText().toString();
                String slogan = sloganEdt.getText().toString();
                String campus = campusEdt.getText().toString();
                String college = collegeEdt.getText().toString();
                if (StringUtil.isStringNull(teamName) || StringUtil.isStringNull(slogan) || StringUtil.isStringNull(campus) || StringUtil.isStringNull(college)) {
                    Toast.makeText(this, "内容不完整", Toast.LENGTH_SHORT).show();
                }else {
                    Team team = new Team();
                    team.setTeamName(teamName);
                    team.setTeamSlogan(slogan);
                    team.setCampus(campus);
                    team.setCollege(college);
                    mTeamController.addTeam(team);
                }

                break;


        }
    }

    @Override
    public void buildTeamBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GroupBuild.this, res ? "success" : "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
