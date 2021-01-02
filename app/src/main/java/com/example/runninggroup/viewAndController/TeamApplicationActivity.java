package com.example.runninggroup.viewAndController;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.TeamApplicationController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamApplication;
import com.example.runninggroup.view.WaringDialogWithTwoButton;
import com.example.runninggroup.viewAndController.adapter.FriendApplicationAdapter;
import com.example.runninggroup.viewAndController.adapter.TeamApplicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamApplicationActivity extends AppCompatActivity implements TeamApplicationController.TeamApplicationControllerInterface {
    private RecyclerView applicationList;
    private TeamApplicationController mTeamApplicationController = new TeamApplicationController(this);
    private List<TeamApplication> mTeamApplications = new ArrayList<>();
    private ImageView backImg;
    private TextView clearTxt;
    private TeamApplicationAdapter mTeamApplicationAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_application);
        initView();
        initEvent();
    }

    private void initEvent() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamApplicationActivity.this, Manage.class);
                startActivity(intent);
            }
        });
        clearTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaringDialogWithTwoButton waringDialogWithTwoButton = new WaringDialogWithTwoButton(TeamApplicationActivity.this, "您确定删除所有申请消息吗？", "取消", "确定");
                waringDialogWithTwoButton.setOnButtonClickListener(new WaringDialogWithTwoButton.OnButtonClickListener() {
                    @Override
                    public void right() {
                        mTeamApplicationController.deleteAllTeamApplication();
                    }

                    @Override
                    public void left() {
                        waringDialogWithTwoButton.dismiss();
                    }
                });
                waringDialogWithTwoButton.show();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTeamApplicationController.getTeamApplication();
            }
        });





    }

    private void initView() {
        applicationList = findViewById(R.id.application);
        backImg = findViewById(R.id.back);
        clearTxt = findViewById(R.id.clear);
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        mTeamApplicationAdapter = new TeamApplicationAdapter(getLayoutInflater(), mTeamApplications, this);
        /**
         * 设置同意or拒绝的点击事件
         */
        mTeamApplicationAdapter.setBtnOnClickListener(new TeamApplicationAdapter.BtnOnClickListener() {
            @Override
            public void acceptOnClick(TeamApplicationAdapter.InnerHolder viewHolder) {
                TeamApplication teamApplication = viewHolder.mTeamApplication;
                mTeamApplicationController.updateTeamApplication(teamApplication.getId(), 2, teamApplication.getUser().getId(), viewHolder);
            }

            @Override
            public void refuseOnClick(TeamApplicationAdapter.InnerHolder viewHolder) {
                TeamApplication teamApplication = viewHolder.mTeamApplication;
                mTeamApplicationController.updateTeamApplication(teamApplication.getId(), 3, teamApplication.getUser().getId(), viewHolder);
            }

            @Override
            public void deleteOnClick(int position) {
                TeamApplication teamApplication = mTeamApplications.get(position);
                mTeamApplicationController.deleteTeamApplication(teamApplication, position);
            }

        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        applicationList.setLayoutManager(layoutManager);
        applicationList.setAdapter(mTeamApplicationAdapter);
        mTeamApplicationController.getTeamApplication();

    }


    /**
     * 跑团申请列表点击同意or拒绝的回调
     * @param res
     * @param state
     * @param viewHolder
     */

    @Override
    public void updateTeamApplicationBack(boolean res, int state, TeamApplicationAdapter.InnerHolder viewHolder) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamApplicationActivity.this,  res ? "success" : "error", Toast.LENGTH_SHORT).show();
                if (res) {
                    viewHolder.acceptBtn.setVisibility(View.INVISIBLE);
                    viewHolder.refuseBtn.setVisibility(View.INVISIBLE);
                    viewHolder.stateText.setText(state == 2 ? "已同意" : "已拒绝");
                }
            }
        });
    }

    /**
     * 获取Application回调
     * @param teamApplications
     */
    @Override
    public void getTeamApplicationBack(List<TeamApplication> teamApplications) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (teamApplications == null) Toast.makeText(TeamApplicationActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else{
                    if (teamApplications.size() > 0) clearTxt.setVisibility(View.VISIBLE);
                    mTeamApplications.clear();
                    mTeamApplications.addAll(teamApplications);
                    mTeamApplicationAdapter.notifyDataSetChanged();
                }
            }
        });
    }



    @Override
    public void deleteTeamApplicationBack(boolean res, int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamApplicationActivity.this, res ? "已删除" : "删除失败" , Toast.LENGTH_SHORT).show();
                if (res)  {
                    mTeamApplications.remove(position);
                    mTeamApplicationAdapter.notifyItemRemoved(position);
                    if (position != mTeamApplications.size()) {
                        mTeamApplicationAdapter.notifyItemRangeChanged(position, mTeamApplications.size() - position);
                    }
                }
            }
        });
    }

    @Override
    public void deleteAllTeamApplicationBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamApplicationActivity.this, res ? "已清空" : "清空失败" , Toast.LENGTH_SHORT).show();
                if (res)  {
                    mTeamApplicationController.getTeamApplication();
                }
            }
        });
    }
}
