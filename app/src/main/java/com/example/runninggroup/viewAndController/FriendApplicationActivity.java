package com.example.runninggroup.viewAndController;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.viewAndController.adapter.FriendApplicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendApplicationActivity extends AppCompatActivity implements FriendApplicationController.FriendApplicationControllerInterface {
    private ListView applicationList;
    private FriendApplicationController mFriendApplicationController = new FriendApplicationController(this);
    private List<FriendApplication> mFriendApplicationList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_application);
        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
        applicationList = findViewById(R.id.application);
        applicationList.setAdapter(new FriendApplicationAdapter(getLayoutInflater(), mFriendApplicationList, this));
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
                    applicationList.setAdapter(new FriendApplicationAdapter(getLayoutInflater(), mFriendApplicationList, FriendApplicationActivity.this));
                }

            }
        });
    }
}
