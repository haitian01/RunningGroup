package com.example.runninggroup.viewAndController;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.controller.TeamApplicationController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StatusBarUtils;

import java.util.List;

public class TeamIntroduction extends AppCompatActivity implements FileController.FileControllerInterface, UserController.UserControllerInterface {
    TextView teamNameTxt, teamRegisterNumTxt, buildTimeTxt, leaderTxt, campusTxt, collegeTxt, sizeTxt, lengthTxt, scoreTxt;
    Button addBtn,deleteBtn;
    RelativeLayout teamManage,teamTask,teamSort, members;
    LinearLayout teamFunction;
    ImageView teamImg;
    private FileController mFileController = new FileController(this);
    private UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_introduction);
        initView();
        initEvent();
    }

    private void initEvent() {
        teamManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cache.user.getTeamLevel() < 2) {
                    Toast.makeText(TeamIntroduction.this, "无管理员权限", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(TeamIntroduction.this, Manage.class);
                    startActivity(intent);
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cache.user.getTeam() != null)
                    Toast.makeText(TeamIntroduction.this, "已加入过跑团！", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(TeamIntroduction.this, AddTeamActivity.class);
                    startActivity(intent);
                }
            }
        });
        teamSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamIntroduction.this, MemberSort.class);
                startActivity(intent);
            }
        });
        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamIntroduction.this, MemberManage.class);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeamIntroduction.this);
                builder.setMessage("您确定退出跑团吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      mUserController.signOutTeam();
                    }
                });
                builder.show();
            }
        });

    }

    private void initView() {
        StatusBarUtils.setStatusBarFullTransparent(this);
        teamNameTxt = findViewById(R.id.teamName);
        teamRegisterNumTxt = findViewById(R.id.teamRegisterNum);
        buildTimeTxt = findViewById(R.id.buildTime);
        addBtn = findViewById(R.id.add);
        teamManage = findViewById(R.id.teamManage);
        teamTask = findViewById(R.id.teamTask);
        teamSort = findViewById(R.id.teamSort);
        teamFunction = findViewById(R.id.teamFunction);
        teamImg = findViewById(R.id.teamImg);
        deleteBtn = findViewById(R.id.delete);
        members = findViewById(R.id.members);
        leaderTxt = findViewById(R.id.leader);
        campusTxt = findViewById(R.id.campus);
        collegeTxt = findViewById(R.id.college);
        sizeTxt = findViewById(R.id.size);
        lengthTxt = findViewById(R.id.length);
        scoreTxt = findViewById(R.id.score);

    }

    @Override
    protected void onResume() {
        super.onResume();

        leaderTxt.setText("团长： " + Cache.team.getUser().getUsername());
        campusTxt.setText("学校： " + Cache.team.getCampus());
        collegeTxt.setText("专业： " + Cache.team.getCollege());
        sizeTxt.setText("规模： " + Cache.team.getTeamSize() + "人");
        scoreTxt.setText("平均分数： " + (double)Cache.team.getScore() / Cache.team.getTeamSize());
        lengthTxt.setText("平均里程： " + (double)Cache.team.getLength() / Cache.team.getTeamSize() + "公里");
        buildTimeTxt.setText("创建时间： " + Cache.team.getCreateTime().toString().substring(0, Cache.team.getCreateTime().toString().lastIndexOf(" ")));


        mFileController.getImg(ImgNameUtil.getGroupHeadImgName(Cache.team.getId()));
        teamNameTxt.setText(Cache.team.getTeamName());
        teamRegisterNumTxt.setText(Cache.team.getRegisterNum());
        //add or delete button

        if (Cache.user.getTeam() == null || Cache.user.getTeam().getId() != Cache.team.getId()) {
            addBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);
        }else {

            if (Cache.user.getTeam().getUser().getId() == Cache.user.getId()) {
                addBtn.setVisibility(View.INVISIBLE);
                deleteBtn.setVisibility(View.INVISIBLE);
            }
        }
        //teamFunction
        if (Cache.user.getTeam() != null && Cache.user.getTeam().getId() == Cache.team.getId()) {
            teamFunction.setVisibility(View.VISIBLE);
        }else {
            teamFunction.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getImgBack(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) teamImg.setImageDrawable(drawable);
            }
        });
    }

    @Override
    public void signOutTeamBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamIntroduction.this, res ? "success" : "error", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(TeamIntroduction.this, TeamIntroduction.class);
                    startActivity(intent);
                }
            }
        });
    }
}
