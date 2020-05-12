package com.example.runninggroup.viewAndController.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.Login;
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
        list=new ArrayList<FriendsHelper>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                list = DaoUser.getFriends(getActivity().getIntent().getStringExtra("username"));
                if (list == null){
//                    Looper.prepare();
//                    Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_SHORT).show();
//                    Looper.loop();
                    list = new ArrayList<FriendsHelper>();
                    Log.v("tag","网络异常");
                }else {
                    for(FriendsHelper friendsHelper:list){
                        friendsHelper.setPic(R.mipmap.defaultpic);
                    }
                }

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
                bundle.putString("name",list.get(position).getUsername());
                bundle.putString("group",list.get(position).getGroupName());
                Intent intent = new Intent(getActivity(), FriendMessage.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {

    }
}
