package com.example.runninggroup.viewAndController.fragment;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendCircleController;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.test.TestLy;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.viewAndController.adapter.FriendCircleAdapter;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentFriendCircle extends Fragment implements FriendCircleController.FriendCircleControllerInterface {
    View mView;
    private RecyclerView mFriendCircleRec;
    private List<FriendCircle> mFriendCircleList = new ArrayList();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendCircleAdapter friendCircleAdapter;
    private FriendCircleController mFriendCircleController = new FriendCircleController(this);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_friend_circle,container,false);
        initView();
        initEvent();
        return mView;
    }

    private void initView() {
        mSwipeRefreshLayout = mView.findViewById(R.id.reload);



        mSwipeRefreshLayout.setEnabled(true);
        mFriendCircleRec = mView.findViewById(R.id.friend_circle_list);

        //设置边距
        ItemDecoration itemDecoration = new ItemDecoration(10);
        mFriendCircleRec.addItemDecoration(itemDecoration);

        //布局
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFriendCircleRec.setLayoutManager(layoutManager);
        //适配器
        friendCircleAdapter = new FriendCircleAdapter(mFriendCircleList, getActivity());
        friendCircleAdapter.setOnRefreshListener(new FriendCircleAdapter.OnRefreshListener() {
            @Override
            public void onUpPullRefresh(FriendCircleAdapter.LoadHolder loadHolder) {

                if (mFriendCircleList.size() > 0) mFriendCircleController.getFriendCircle(mFriendCircleList.get(mFriendCircleList.size() - 1).getId(), loadHolder);
                else {
                    loadHolder.update(FriendCircleAdapter.LoadHolder.NORMAL);
                }
            }
        });
        //点赞
        friendCircleAdapter.setControllerOnClickListener(new FriendCircleAdapter.ControllerOnClickListener() {
            @Override
            public void zanOnClick(FriendCircleAdapter.InnerHolder innerHolder) {
                mFriendCircleController.updateZan(innerHolder);

            }

            @Override
            public void commentOnClick(FriendCircleAdapter.InnerHolder innerHolder) {
                Toast.makeText(getActivity(), "comment", Toast.LENGTH_SHORT).show();
            }
        });
        friendCircleAdapter.setImgBackListener(new FriendCircleAdapter.ImgBackListener() {
            @Override
            public void imgBack(Drawable drawable, FriendCircleAdapter.InnerHolder innerHolder) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null) innerHolder.headImg.setImageDrawable(drawable);
                    }
                });
            }
        });
        mFriendCircleRec.setAdapter(friendCircleAdapter);
        mFriendCircleController.getFriendCircleFirst();

    }



    private void initEvent () {
        //下拉刷新
        handleDownPullUpdate();


    }

    private void handleDownPullUpdate() {
        mSwipeRefreshLayout.setEnabled(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFriendCircleController.getFriendCircleFirst();
            }
        });
    }


    //点赞回调
    @Override
    public void updateZanBack(boolean res, FriendCircleAdapter.InnerHolder innerHolder) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    int up = innerHolder.mFriendCircle.getZanNum() + 1;
                    int down = innerHolder.mFriendCircle.getZanNum() - 1;
                    String zanGroup = innerHolder.mFriendCircle.getZanGroup();
                    if (zanGroup == null || zanGroup.length() == 0) {
                        innerHolder.zanNum.setText(up + "");
                        innerHolder.mFriendCircle.setZanGroup(Cache.user.getId() + ",");
                        innerHolder.mFriendCircle.setZanNum(up);
                        innerHolder.zanImg.setImageResource(R.drawable.zan_after);
                    }else {
                        String[] split = zanGroup.split(",");
                        StringBuilder newZanGroup = new StringBuilder();
                        boolean add = true;
                        for (String s : split) {
                            if (s.equals(Cache.user.getId() + "")) add = false;
                            else newZanGroup.append(s + ",");
                        }
                        if (add) newZanGroup.append(Cache.user.getId()).append(",");
                        innerHolder.mFriendCircle.setZanGroup(newZanGroup.toString());
                        innerHolder.zanNum.setText(add ? up + "" : down + "");
                        innerHolder.mFriendCircle.setZanNum(add ? up : down);
                        innerHolder.zanImg.setImageResource(add ? R.drawable.zan_after : R.drawable.zan
                        );
                    }
                }
            }
        });
    }

    @Override
    public void getFriendCircleFirstBack(List<FriendCircle> friendCircleList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendCircleList == null) {
                    Toast.makeText(getActivity(), "网络故障", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                else {
                    ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
                    if (viewPager.getCurrentItem() == ConstantUtil.FRAGMENT_FRIEND_CIRCLE)
                        Toast.makeText(getActivity(),  "拉取最新动态 " + friendCircleList.size() + " 条", Toast.LENGTH_SHORT).show();
                    mFriendCircleList.clear();
                    mFriendCircleList.addAll(friendCircleList);
                    friendCircleAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void getFriendCircleBack(List<FriendCircle> friendCircleList, FriendCircleAdapter.LoadHolder loadHolder) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendCircleList == null) {
                    Toast.makeText(getActivity(), "网络故障", Toast.LENGTH_SHORT).show();
                    loadHolder.update(FriendCircleAdapter.LoadHolder.LOAD_FAIL);
                }
                else {
                    if (friendCircleList.size() != 0){
                        mFriendCircleList.addAll(friendCircleList);
                        friendCircleAdapter.notifyDataSetChanged();
                    }
                    loadHolder.update(FriendCircleAdapter.LoadHolder.NORMAL);

                }

            }
        });
    }

    public class ItemDecoration extends RecyclerView.ItemDecoration {
        public int it;
        public ItemDecoration(int it){
            this.it=it;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=it;
            outRect.right=it;
            outRect.bottom=it;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=it;
            }
        }
    }
}
