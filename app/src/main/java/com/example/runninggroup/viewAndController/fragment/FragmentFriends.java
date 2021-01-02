package com.example.runninggroup.viewAndController.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.FriendSearchActivity;
import com.example.runninggroup.viewAndController.adapter.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentFriends extends Fragment implements FriendRelationController.FriendRelationControllerInterface {
    public RecyclerView friendListRecy;
    public List<FriendRelation> list = new ArrayList<>();
    public View view;
    private RelativeLayout loadImg_out, searchRela;
    private FriendRelationController mFriendRelationController = new FriendRelationController(this);
    private Animation animation;
    private ImageView loadImg;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendsAdapter mFriendsAdapter;
    String username;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        username = getActivity().getIntent().getStringExtra("username");



        view =inflater.inflate(R.layout.fragment_friends,container,false);
        initView();

        initEvent();
        return view;
    }

    private void initEvent() {
        handleDownPullUpdate();


        searchRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendSearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView() {

        mSwipeRefreshLayout = view.findViewById(R.id.reload);
        loadImg = view.findViewById(R.id.loadImg);
        loadImg_out = view.findViewById(R.id.loadImg_out);
        searchRela = view.findViewById(R.id.search);
        //find
        friendListRecy=view.findViewById(R.id.friend_list);
        mFriendsAdapter = new FriendsAdapter(this.getLayoutInflater(),list, getActivity());
        mFriendsAdapter.setOnItemClickListener(new FriendsAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                if (list != null && list.size() > position) {
                    Cache.friend = list.get(position).getFriend();
                    Intent intent = new Intent(getActivity(), FriendMessage.class);
                    startActivity(intent);
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置适配器
        friendListRecy.setAdapter(mFriendsAdapter);
        friendListRecy.setLayoutManager(layoutManager);



        //动画效果
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
        animation.setDuration(500);
        animation.setRepeatCount(8);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        loadImg.startAnimation(animation);//開始动画
        loadImg_out.setVisibility(View.VISIBLE);
        mFriendRelationController.getFriends();

    }

//    /**
//     * 设置listview布局
//     */
//    public void setListViewLayout() {
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 70 * list.size()));
//        layoutParams.addRule(RelativeLayout.BELOW, R.id.search);
//        mListView.setLayoutParams(layoutParams);
//
//    }

    /**
     * 下拉刷新
     */
    private void handleDownPullUpdate() {
        mSwipeRefreshLayout.setEnabled(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFriendRelationController.getFriends();
            }
        });
    }

    @Override
    public void getFriendsBack(List<FriendRelation> friendRelationList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadImg.clearAnimation();
                loadImg_out.setVisibility(View.INVISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
                if (friendRelationList == null) Toast.makeText(getActivity(), "网络故障", Toast.LENGTH_SHORT).show();
                else  {
                    list.clear();
                    list.addAll(friendRelationList);
                    mFriendsAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}
