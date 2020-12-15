package com.example.runninggroup.viewAndController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.controller.TeamController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.adapter.FriendRelationAdapter;
import com.example.runninggroup.viewAndController.adapter.SearchAdapter;
import com.example.runninggroup.viewAndController.adapter.TeamAdapter;
import com.example.runninggroup.viewAndController.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendSearchActivity extends AppCompatActivity implements FriendRelationController.FriendRelationControllerInterface {
    ListView resListView;
    ImageView deleteImg;
    TextView cancelText;
    EditText msgEdt;
    FriendRelationController mFriendRelationController = new FriendRelationController(this);
    List<FriendRelation> mFriendRelationList = new ArrayList<>(); //所有好友
    List<FriendRelation> mFriendRelationListAfterSelect = new ArrayList<>(); // 根据关键词筛选过的好友列表
    private FriendRelationAdapter mFriendRelationAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        initView();
        initEvent();
    }

    private void initEvent() {
        msgEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtil.isStringNull(msgEdt.getText().toString())) {
                    deleteImg.setVisibility(View.INVISIBLE);
                    mFriendRelationListAfterSelect.clear();
                    resListView.setAdapter(mFriendRelationAdapter);
                }else {
                    deleteImg.setVisibility(View.VISIBLE);
                    selectFriendRelationByWord(msgEdt.getText().toString());
                }
            }
        });
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgEdt.setText("");
            }
        });
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
        resListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (mFriendRelationListAfterSelect != null && mFriendRelationListAfterSelect.get(position).getFriend() != null) {
                   Cache.friend = mFriendRelationListAfterSelect.get(position).getFriend();
                   Intent intent = new Intent(FriendSearchActivity.this, FriendMessage.class);
                   startActivity(intent);
               }
            }
        });

    }

    private void initView() {
        deleteImg = findViewById(R.id.delete);
        cancelText = findViewById(R.id.cancel);
        msgEdt = findViewById(R.id.msg);
        resListView = findViewById(R.id.res_list);
        mFriendRelationAdapter = new FriendRelationAdapter(mFriendRelationListAfterSelect, this, null);
        resListView.setAdapter(mFriendRelationAdapter);

        /**
         * 开始加载好友列表
         */
        mFriendRelationController.getFriends();
    }

    @Override
    public void getFriendsBack(List<FriendRelation> friendRelationList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendRelationList != null) {
                    mFriendRelationList = friendRelationList;
                }
            }
        });
    }

    public void selectFriendRelationByWord (String word) {
        mFriendRelationListAfterSelect.clear();
        for (FriendRelation friendRelation : mFriendRelationList) {
            if (friendRelation.getFriend() != null && (friendRelation.getFriend().getUsername().contains(word) || (friendRelation.getAlias() != null && friendRelation.getAlias().contains(word)))) {
                mFriendRelationListAfterSelect.add(friendRelation);
            }
        }
        mFriendRelationAdapter = new FriendRelationAdapter(mFriendRelationListAfterSelect, FriendSearchActivity.this, word);
        resListView.setAdapter(mFriendRelationAdapter);

    }
}
