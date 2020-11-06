package com.example.runninggroup.viewAndController;

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
    TextView teamNameTxt, teamRegisterNumTxt, buildTimeTxt;
    Button addBtn,deleteBtn;
    RelativeLayout teamManage,teamTask,teamSort;
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
        mFileController.getImg(ImgNameUtil.getGroupHeadImgName(Cache.team.getId()));
        deleteBtn = findViewById(R.id.delete);
        teamNameTxt.setText(Cache.team.getTeamName());
        teamRegisterNumTxt.setText(Cache.team.getRegisterNum());
        buildTimeTxt.setText(Cache.team.getCreateTime().toString());
        //add or delete button
        if (Cache.user.getTeam() == null || Cache.user.getTeam().getId() != Cache.team.getId()) {
            addBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);
        }else {

            if (Cache.team.getUser().getId() == Cache.user.getId()) {
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

}
