package com.example.runninggroup.viewAndController.fragment;

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

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.TeamController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.viewAndController.TeamIntroduction;
import com.example.runninggroup.viewAndController.adapter.TeamAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentTeamSearch extends Fragment implements TeamController.TeamControllerInterface{
    View view;
    ListView mListView;
    List<Team> teamList = new ArrayList<>();
    TeamController mTeamController = new TeamController(this);
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
        mListView = view.findViewById(R.id.groupList);
        mListView.setAdapter(new TeamAdapter(getLayoutInflater(), teamList, getActivity() ));
        mTeamController.getTeam(new Team());
    }

    @Override
    public void getTeamBack(List<Team> teams) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (teams == null) {
                    Toast.makeText(getActivity(), teams.size() + "", Toast.LENGTH_SHORT).show();
                }
                else if (teams.size() > 0) {
                    teamList = teams;
                    mListView.setAdapter(new TeamAdapter(getLayoutInflater(), teamList, getActivity() ));
                }
            }
        });
    }


}
