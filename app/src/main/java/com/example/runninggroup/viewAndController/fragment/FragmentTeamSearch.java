package com.example.runninggroup.viewAndController.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.TeamController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.viewAndController.AddTeamActivity;
import com.example.runninggroup.viewAndController.TeamIntroduction;
import com.example.runninggroup.viewAndController.adapter.TeamAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentTeamSearch extends Fragment implements TeamController.TeamControllerInterface, UserController.UserControllerInterface {
    View view;
    RecyclerView groupListRecy;
    List<Team> teamList = new ArrayList<>();
    TeamController mTeamController = new TeamController(this);
    private UserController mUserController = new UserController(this);
    private TeamAdapter mTeamAdapter;
    private Activity mActivity;
    private static final int TEAM_ITEM = 0;
    private static final int ADD_BUTTON = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_team_search,container,false);
        initView();
        initEvent();
        return view;

    }

    private void initEvent() {


    }

    private void initView() {
        mActivity = getActivity();
        groupListRecy = view.findViewById(R.id.groupList);
        mTeamAdapter = new TeamAdapter(getLayoutInflater(), teamList, getActivity());
        mTeamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            //点击跑团的item
            @Override
            public void itemOnClick(int position) {
                Cache.team = teamList.get(position);
                User user = new User();
                user.setId(Cache.user.getId());
                mUserController.selectUserByUserWithType(user, TEAM_ITEM);
            }

            //点击添加的button
            @Override
            public void addOnClick(int position) {
                Cache.team = teamList.get(position);
                User user = new User();
                user.setId(Cache.user.getId());
                mUserController.selectUserByUserWithType(user, ADD_BUTTON);

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        groupListRecy.setAdapter(mTeamAdapter);
        groupListRecy.setLayoutManager(layoutManager);
        mTeamController.getTeam(new Team());
    }

    @Override
    public void getTeamBack(List<Team> teams) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (teams == null) {
                    Toast.makeText(getActivity(), "网络故障", Toast.LENGTH_SHORT).show();
                }
                else if (teams.size() > 0) {
                    teamList.clear();
                    teamList.addAll(teams);
                    mTeamAdapter.notifyDataSetChanged();
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
