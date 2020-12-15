package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.TeamNoticeController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamNotice;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.adapter.TeamNoticeAdapter;

import java.util.ArrayList;
import java.util.List;

public class TeamNoticeActivity extends AppCompatActivity implements TeamNoticeController.TeamNoticeControllerInterface {
    private ImageView backImg;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView noticeRecy;
    private TeamNoticeAdapter mTeamNoticeAdapter;
    private List<TeamNotice> mTeamNoticeList = new ArrayList<>();
    private TeamNoticeController mTeamNoticeController = new TeamNoticeController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_notice);
        initView();

        initEvent();
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyTeamNotice();
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
        noticeRecy = findViewById(R.id.notice_recy);
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        backImg = findViewById(R.id.back);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mTeamNoticeAdapter = new TeamNoticeAdapter(mTeamNoticeList, this);
        mTeamNoticeAdapter.setOnItemClickListener(new TeamNoticeAdapter.OnItemClickListener() {
            @Override
            public void zeroClick(int position) {
                Cache.teamNotice = mTeamNoticeList.get(position);
                Intent intent = new Intent(TeamNoticeActivity.this, TeamNoticePage.class);
                startActivity(intent);
            }
        });

        ItemDecoration itemDecoration = new ItemDecoration(20);
        noticeRecy.setLayoutManager(layoutManager);
        noticeRecy.setAdapter(mTeamNoticeAdapter);
        noticeRecy.addItemDecoration(itemDecoration);


       getMyTeamNotice();



    }
    /**
     * 网络请求所有的公告
     */
    private void getMyTeamNotice () {
        TeamNotice teamNotice = new TeamNotice();
        Team team = new Team ();
        team.setId(Cache.team.getId());
        teamNotice.setTeam(team);
        mTeamNoticeController.getTeamNotice(teamNotice);
    }


    @Override
    public void getTeamNoticeBack(List<TeamNotice> teamNoticeList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (teamNoticeList == null)
                    Toast.makeText(TeamNoticeActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    mTeamNoticeList.clear();
                    mTeamNoticeList.addAll(teamNoticeList);
                    mTeamNoticeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * RecycleView设置间距
     */
    public class ItemDecoration extends RecyclerView.ItemDecoration {
        public int it;
        public ItemDecoration(int it){
            this.it=it;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom=it;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=it;
            }
        }
    }
}
