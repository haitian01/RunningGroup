package com.example.runninggroup.viewAndController.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.DensityUtil;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.FriendSearchActivity;
import com.example.runninggroup.viewAndController.Login;
import com.example.runninggroup.viewAndController.adapter.FriendsAdapter;
import com.example.runninggroup.viewAndController.adapter.MomentAdapter;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.MomentHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentFriends extends Fragment implements FriendRelationController.FriendRelationControllerInterface {
    public ListView mListView;
    public List<FriendRelation> list = new ArrayList<>();
    public View view;
    private RelativeLayout loadImg_out, searchRela;
    private FriendRelationController mFriendRelationController = new FriendRelationController(this);
    private Animation animation;
    private ImageView loadImg;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cache.friend = list.get(position).getFriend();

                Intent intent = new Intent(getActivity(), FriendMessage.class);
                intent.putExtra("alias", list.get(position).getAlias());
                intent.putExtra("fromActivity", ConstantUtil.MAIN_INTERFACE);
                startActivity(intent);
            }
        });

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
        mListView=view.findViewById(R.id.listView);
        setListViewLayout();
        //设置适配器
        mListView.setAdapter(new FriendsAdapter(this.getLayoutInflater(),list, getActivity()));



        //动画效果
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
        animation.setDuration(500);
        animation.setRepeatCount(8);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        loadImg.startAnimation(animation);//開始动画
        loadImg_out.setVisibility(View.VISIBLE);
        mFriendRelationController.getFriends();

    }

    /**
     * 设置listview布局
     */
    public void setListViewLayout() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 70 * list.size()));
        layoutParams.addRule(RelativeLayout.BELOW, R.id.search);
        mListView.setLayoutParams(layoutParams);

    }

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
                    list = friendRelationList;
                    setListViewLayout();
                    mListView.setAdapter(new FriendsAdapter(getActivity().getLayoutInflater(),friendRelationList,getActivity()));
                }
            }
        });

    }
}
