package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.TeamNoticeController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamNotice;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.adapter.TeamNoticeAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamMessageChangeActivity extends AppCompatActivity{
    private ImageView backImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_message_change);
        initView();

        initEvent();
    }

    private void initView() {
        backImg = findViewById(R.id.back);
    }

    private void initEvent() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
    }


}
