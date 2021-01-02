package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;

import java.util.List;

public class FriendAliasChangeActivity extends AppCompatActivity implements FriendRelationController.FriendRelationControllerInterface {
    private EditText aliasEdt;
    private ImageView backImg, clearImg;
    private TextView defineTxt;
    private FriendRelationController mFriendRelationController = new FriendRelationController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_alias_change);
        initView();
        intEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Cache.user != null && Cache.friend != null) {
            FriendRelation friendRelation = new FriendRelation();
            User user = new User();
            User friend = new User();
            user.setId(Cache.user.getId());
            friend.setId(Cache.friend.getId());
            friendRelation.setUser(user);
            friendRelation.setFriend(friend);
            mFriendRelationController.getFriendRelation(friendRelation);
        }
    }

    private void intEvent() {
        clearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliasEdt.setText("");
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
        defineTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (Cache.user != null && Cache.friend != null) {
                   String alias = aliasEdt.getText().toString();
                   FriendRelation friendRelation = new FriendRelation();
                   User user = new User();
                   user.setId(Cache.user.getId());
                   User friend = new User();
                   friend.setId(Cache.friend.getId());
                   friendRelation.setUser(user);
                   friendRelation.setFriend(friend);
                   friendRelation.setAlias(StringUtil.isStringNull(alias) ? null : alias);
                   mFriendRelationController.updateFriendRelation(friendRelation);
               }
            }
        });

    }

    private void initView() {
        aliasEdt = findViewById(R.id.alias_edt);
        backImg = findViewById(R.id.back);
        clearImg = findViewById(R.id.clear);
        defineTxt = findViewById(R.id.define);
    }

    @Override
    public void updateFriendRelationBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FriendAliasChangeActivity.this, FriendSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getFriendRelationBack(List<FriendRelation> friendRelationList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendRelationList != null && friendRelationList.size() > 0 && Cache.friend != null) {
                    String alias = friendRelationList.get(0).getAlias();
                    aliasEdt.setText(alias == null ? Cache.friend.getUsername() : alias);
                }
            }
        });
    }
}
