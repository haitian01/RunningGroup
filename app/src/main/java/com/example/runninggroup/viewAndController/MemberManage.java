package com.example.runninggroup.viewAndController;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.adapter.MemberManageAdapter;


import java.util.ArrayList;
import java.util.List;

public class MemberManage extends AppCompatActivity implements UserController.UserControllerInterface {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ListView memberListView;
    List<User> userList = new ArrayList<>();
    private ImageView backImg;
    private UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membermanage);
        initView();
        initEvent();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getAllMember();
    }
    private void getAllMember () {
        User user = new User();
        Team team = new Team();
        team.setId(Cache.user.getTeam().getId());
        user.setTeam(team);
        mUserController.selectUserByUser(user);
    }

    private void initView() {
        memberListView = findViewById(R.id.memberListView);
        backImg = findViewById(R.id.back);
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        memberListView.setAdapter(new MemberManageAdapter(this,userList));



    }
    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMember();
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });

    }

    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (users == null)
                    Toast.makeText(MemberManage.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() == 0)
                    Toast.makeText(MemberManage.this, "error", Toast.LENGTH_SHORT).show();
                else {
                    userList = users;
                    memberListView.setAdapter(new MemberManageAdapter(MemberManage.this, userList));
                }
            }
        });
    }



}
