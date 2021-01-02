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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.view.WaringDialogWithTwoButton;
import com.example.runninggroup.viewAndController.adapter.FriendApplicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendApplicationActivity extends AppCompatActivity implements FriendApplicationController.FriendApplicationControllerInterface {
    private RecyclerView applicationList;
    private FriendApplicationController mFriendApplicationController = new FriendApplicationController(this);
    private List<FriendApplication> mFriendApplicationList = new ArrayList<>();
    private FriendApplicationAdapter mFriendApplicationAdapter;
    private ImageView backImg;
    private TextView clearTxt;
    private SwipeRefreshLayout refresh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_application);
        initView();
        initEvent();
    }


    private void initEvent() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFriendApplicationController.getApplication();
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
        clearTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaringDialogWithTwoButton waringDialogWithTwoButton = new WaringDialogWithTwoButton(FriendApplicationActivity.this, "您确定删除所有申请消息吗？", "取消", "确定");
                waringDialogWithTwoButton.setOnButtonClickListener(new WaringDialogWithTwoButton.OnButtonClickListener() {
                    @Override
                    public void right() {
                        mFriendApplicationController.deleteAllFriendApplication();
                    }

                    @Override
                    public void left() {
                        waringDialogWithTwoButton.dismiss();
                    }
                });
                waringDialogWithTwoButton.show();
            }
        });

    }

    private void initView() {
        applicationList = findViewById(R.id.application);
        backImg = findViewById(R.id.back);
        clearTxt = findViewById(R.id.clear);
        refresh = findViewById(R.id.refresh);
        mFriendApplicationAdapter = new FriendApplicationAdapter(getLayoutInflater(), mFriendApplicationList, this);
        mFriendApplicationAdapter.setOnButtonClickListener(new FriendApplicationAdapter.OnButtonClickListener() {
            @Override
            public void accept(int position) {
                FriendApplication friendApplication = mFriendApplicationList.get(position);
                friendApplication.setState(2);
                mFriendApplicationController.agreeToRefuse(friendApplication, position);
            }

            @Override
            public void refuse(int position) {
                FriendApplication friendApplication = mFriendApplicationList.get(position);
                friendApplication.setState(3);
                mFriendApplicationController.agreeToRefuse(friendApplication, position);
            }

            @Override
            public void delete(int position) {
                FriendApplication friendApplication = mFriendApplicationList.get(position);
                mFriendApplicationController.deleteFriendApplication(friendApplication, position);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        applicationList.setAdapter(mFriendApplicationAdapter);
        applicationList.setLayoutManager(layoutManager);
        mFriendApplicationController.getApplication();
    }



    @Override
    public void getApplicationBack(List<FriendApplication> friendApplicationList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
                if (friendApplicationList == null)
                    Toast.makeText(FriendApplicationActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    if (friendApplicationList.size() > 0) clearTxt.setVisibility(View.VISIBLE);
                    mFriendApplicationList.clear();
                    mFriendApplicationList.addAll(friendApplicationList);
                    mFriendApplicationAdapter.notifyDataSetChanged();
                }

            }
        });
    }


    @Override
    public void agreeToRefuseBack(boolean res, int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (res) {
                    Toast.makeText(FriendApplicationActivity.this, mFriendApplicationList.get(position).getState() == 2 ? "已同意" : "已拒绝", Toast.LENGTH_SHORT).show();
                    mFriendApplicationAdapter.notifyItemChanged(position);
                }


            }
        });
    }

    @Override
    public void deleteFriendApplicationBack(boolean res, int position, FriendApplication friendApplication) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FriendApplicationActivity.this, res ? "已删除" : "删除失败", Toast.LENGTH_SHORT).show();
                if (res) {
                    mFriendApplicationList.remove(friendApplication);
                    mFriendApplicationAdapter.notifyItemRemoved(position);
                    if (position != mFriendApplicationList.size()) {
                        mFriendApplicationAdapter.notifyItemRangeChanged(position, mFriendApplicationList.size() - position);
                    }
                }
            }
        });
    }

    @Override
    public void deleteAllFriendApplicationBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FriendApplicationActivity.this, res ? "已清空" : "清空失败", Toast.LENGTH_SHORT).show();
                if (res) mFriendApplicationController.getApplication();
            }
        });
    }
}
