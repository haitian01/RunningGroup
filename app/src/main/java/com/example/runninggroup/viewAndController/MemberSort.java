package com.example.runninggroup.viewAndController;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.viewAndController.adapter.MemberAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberSort extends AppCompatActivity implements UserController.UserControllerInterface {
    ListView memberList;
    List<User> mUsers = new ArrayList<>();
    private UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_sort);
        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
        memberList = findViewById(R.id.memberList);
        memberList.setAdapter(new MemberAdapter(mUsers, this, "length"));
        User user = new User();
        user.setTeam(Cache.user.getTeam());
        mUserController.selectUserByUser(user);
    }

    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(MemberSort.this, "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    mUsers = users;
                    memberList.setAdapter(new MemberAdapter(mUsers, MemberSort.this, "length"));
                }
            }
        });
    }
}
