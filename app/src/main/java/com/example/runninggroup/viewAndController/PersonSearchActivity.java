package com.example.runninggroup.viewAndController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.TeamController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.adapter.FriendsAdapter;
import com.example.runninggroup.viewAndController.adapter.MemberAdapter;
import com.example.runninggroup.viewAndController.adapter.SearchAdapter;
import com.example.runninggroup.viewAndController.adapter.TeamAdapter;
import com.example.runninggroup.viewAndController.adapter.UserAdapter;

import java.util.List;

public class PersonSearchActivity extends AppCompatActivity implements UserController.UserControllerInterface, TeamController.TeamControllerInterface {
    ListView searchListView;
    RecyclerView resListView;
    ImageView deleteImg;
    TextView cancelText;
    EditText msgEdt;
    UserController mUserController = new UserController(this);
    TeamController mTeamController = new TeamController(this);
    List<User> mUserList;
    List<Team> mTeamList;
    private Activity mActivity;
    private static final int TEAM_ITEM = 0;
    private static final int ADD_BUTTON = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_search);
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
                mTeamList = null;
                mUserList = null;
                resListView.setVisibility(View.INVISIBLE);
                if (StringUtil.isStringNull(msgEdt.getText().toString())) {
                    searchListView.setVisibility(View.INVISIBLE);
                    deleteImg.setVisibility(View.INVISIBLE);
                }
                if (! StringUtil.isStringNull(msgEdt.getText().toString())) {
                    searchListView.setAdapter(new SearchAdapter(getLayoutInflater(), msgEdt.getText().toString()));
                    searchListView.setVisibility(View.VISIBLE);
                    deleteImg.setVisibility(View.VISIBLE);
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
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = msgEdt.getText().toString();
                if (position == 0) mUserController.selectUser(msg);
                else if (position == 1) mTeamController.getTeamByMsg(msg);
            }
        });

    }

    private void initView() {
        mActivity = this;
        searchListView = findViewById(R.id.search_list);
        deleteImg = findViewById(R.id.delete);
        cancelText = findViewById(R.id.cancel);
        msgEdt = findViewById(R.id.msg);
        resListView = findViewById(R.id.res_list);
    }

    @Override
    public void selectUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(PersonSearchActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() == 0)
                    Toast.makeText(PersonSearchActivity.this, "未找到", Toast.LENGTH_SHORT).show();
                else if (users.size() == 1) {
                    Cache.friend = users.get(0);
                    Intent intent = new Intent(getApplicationContext(), FriendMessage.class);
                    intent.putExtra("fromActivity", ConstantUtil.PERSON_SEARCH);
                    startActivity(intent);
                } else {
                    Toast.makeText(PersonSearchActivity.this, "找到" + users.size() + "人", Toast.LENGTH_SHORT).show();
                    mUserList = users;
                    resListView.setVisibility(View.VISIBLE);
                    UserAdapter userAdapter = new UserAdapter(mUserList, PersonSearchActivity.this);
                    userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Cache.friend = mUserList.get(position);
                            Intent intent = new Intent(PersonSearchActivity.this, FriendMessage.class);
                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PersonSearchActivity.this);
                    resListView.setAdapter(userAdapter);
                    resListView.setLayoutManager(layoutManager);
                }
            }
        });
    }

    @Override
    public void getTeamByMsgBack(List<Team> teams) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (teams == null)
                    Toast.makeText(PersonSearchActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (teams.size() == 0)
                    Toast.makeText(PersonSearchActivity.this, "未找到", Toast.LENGTH_SHORT).show();
                else if (teams.size() == 1) {
                    Cache.team = teams.get(0);
                    Intent intent = new Intent(getApplicationContext(), TeamIntroduction.class);
                    intent.putExtra("fromActivity", ConstantUtil.PERSON_SEARCH);
                    startActivity(intent);
                } else {
                    Toast.makeText(PersonSearchActivity.this, "找到" + teams.size() + "个", Toast.LENGTH_SHORT).show();
                    mTeamList = teams;
                    resListView.setVisibility(View.VISIBLE);
                    TeamAdapter teamAdapter = new TeamAdapter(getLayoutInflater(), mTeamList, PersonSearchActivity.this);
                    teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
                        @Override
                        public void itemOnClick(int position) {
                            Cache.team = mTeamList.get(position);
                            User user = new User();
                            user.setId(Cache.user.getId());
                            mUserController.selectUserByUserWithType(user, TEAM_ITEM);
                        }

                        @Override
                        public void addOnClick(int position) {
                            Cache.team = mTeamList.get(position);
                            User user = new User();
                            user.setId(Cache.user.getId());
                            mUserController.selectUserByUserWithType(user, ADD_BUTTON);
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PersonSearchActivity.this);
                    resListView.setLayoutManager(layoutManager);
                    resListView.setAdapter(teamAdapter);
                }
            }
        });
    }
    @Override
    public void selectUserByUserWithTypeBack(List<User> users, int type) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null) Toast.makeText(mActivity, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() == 0) Toast.makeText(mActivity, "程序错误", Toast.LENGTH_SHORT).show();
                else {
                    Cache.user = users.get(0);
                    if (type == TEAM_ITEM) {
                        Intent intent = new Intent(mActivity, TeamIntroduction.class);
                        mActivity.startActivity(intent);
                    }else if (type == ADD_BUTTON) {
                        if (Cache.user.getTeam() == null) {
                            Intent intent = new Intent(mActivity, AddTeamActivity.class);
                            mActivity.startActivity(intent);
                        }else {
                            Toast.makeText(mActivity, "已经加入过跑团！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }
}
