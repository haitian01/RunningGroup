package com.example.runninggroup.viewAndController;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendCircleController;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.view.WaringDialogWithTwoButton;

import java.util.List;

public class FriendSettingActivity extends AppCompatActivity implements FriendRelationController.FriendRelationControllerInterface {
    private TextView aliasTxt, deleteTxt;
    private RelativeLayout aliasItem;
    private ImageView backImg;
    private FriendRelationController mFriendRelationController = new FriendRelationController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_setting);
        initView();
        initEvent();
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

    private void initEvent() {
        deleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaringDialogWithTwoButton waringDialogWithTwoButton = new WaringDialogWithTwoButton(FriendSettingActivity.this, "您确定删除该好友吗？", "取消", "确定");
                waringDialogWithTwoButton.setOnButtonClickListener(new WaringDialogWithTwoButton.OnButtonClickListener() {
                    @Override
                    public void right() {
                        waringDialogWithTwoButton.dismiss();
                        if (Cache.user != null && Cache.friend != null) mFriendRelationController.deleteFriendRelation(Cache.user.getId(), Cache.friend.getId());
                    }

                    @Override
                    public void left() {
                        waringDialogWithTwoButton.dismiss();
                    }
                });
                waringDialogWithTwoButton.show();
            }
        });
        aliasItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendSettingActivity.this, FriendAliasChangeActivity.class);
                startActivity(intent);
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });


    }

    private void initView() {
        aliasItem = findViewById(R.id.alias_item);
        aliasTxt = findViewById(R.id.alias);
        deleteTxt = findViewById(R.id.delete);
        backImg = findViewById(R.id.back);
    }

    @Override
    public void deleteFriendRelationBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FriendSettingActivity.this, res ? "已删除" : "删除失败" , Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(FriendSettingActivity.this, FriendMessage.class);
                    startActivity(intent);
                }
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
                    aliasTxt.setText(alias == null ? "" : alias);
                }
            }
        });
    }
}
