package com.example.runninggroup.viewAndController.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.adapter.FriendsAdapter;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentFriends extends Fragment {
    public ListView mListView;
    public List<FriendsHelper> list;
    public FriendsAdapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list=new ArrayList<>();

        list.add(new FriendsHelper(R.mipmap.friendshelper,"张三","北邮跑团",150,20));
        list.add(new FriendsHelper(R.mipmap.friendshelper,"李四","天大跑团",180,30));
        list.add(new FriendsHelper(R.mipmap.friendshelper,"王五","南开跑团",200,160));
        list.add(new FriendsHelper(R.mipmap.friendshelper,"赵六","复旦跑团",300,500));
        list.add(new FriendsHelper(R.mipmap.friendshelper,"周七","上交跑团",1500,600));
        View view =inflater.inflate(R.layout.fragment_friends,container,false);
        //find
        mListView=view.findViewById(R.id.listView);
        //初始化适配器
        mAdapter=new FriendsAdapter(this.getLayoutInflater(),list);
        //设置适配器
        mListView.setAdapter(mAdapter);

        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("name",list.get(position).getName());
                bundle.putString("group",list.get(position).getGroup());
                Intent intent = new Intent(getActivity(), FriendMessage.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {

    }
}
