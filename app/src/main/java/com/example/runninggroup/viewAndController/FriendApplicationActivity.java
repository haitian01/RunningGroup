package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.adapter.FriendApplicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendApplicationActivity extends AppCompatActivity implements FriendApplicationController.FriendApplicationControllerInterface {
    private ListView applicationList;
    private FriendApplicationController mFriendApplicationController = new FriendApplicationController(this);
    private List<FriendApplication> mFriendApplicationList = new ArrayList<>();
    private ImageView backImg;
    private TextView clearTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_application);
        initView();
        initEvent();
    }

    private void initEvent() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
        clearTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendApplication friendApplication = new FriendApplication();
                User user = new User();
                user.setId(Cache.user.getId());
                friendApplication.setUser(user);
                mFriendApplicationController.deleteFriendApplication(friendApplication);
            }
        });

    }

    private void initView() {
        applicationList = findViewById(R.id.application);
        backImg = findViewById(R.id.back);
        clearTxt = findViewById(R.id.clear);
        applicationList.setAdapter(new FriendApplicationAdapter(getLayoutInflater(), mFriendApplicationList, this, this));
        mFriendApplicationController.getApplication();
    }

    @Override
    public void getApplicationBack(List<FriendApplication> friendApplicationList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendApplicationList == null)
                    Toast.makeText(FriendApplicationActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    mFriendApplicationList = friendApplicationList;
                    applicationList.setAdapter(new FriendApplicationAdapter(getLayoutInflater(), mFriendApplicationList, FriendApplicationActivity.this, FriendApplicationActivity.this ));
                }

            }
        });
    }

    @Override
    public void agreeToRefuseBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res) {
                    mFriendApplicationController.getApplication();
                    Toast.makeText(FriendApplicationActivity.this, "success", Toast.LENGTH_SHORT).show();
                }

                else Toast.makeText(FriendApplicationActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteFriendApplicationBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FriendApplicationActivity.this, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) mFriendApplicationController.getApplication();
            }
        });
    }
}
