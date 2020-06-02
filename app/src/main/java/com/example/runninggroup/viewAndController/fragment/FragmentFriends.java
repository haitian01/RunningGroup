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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.Login;
import com.example.runninggroup.viewAndController.adapter.FriendsAdapter;
import com.example.runninggroup.viewAndController.adapter.MomentAdapter;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.MomentHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentFriends extends Fragment {
    public ListView mListView,mListView1;
    public List<FriendsHelper> list;
    public List<MomentHelper> list1;
    public TextView applicationTextView,friendTextView;
    public View view;
    private RelativeLayout mRelativeLayout;
    String username;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        username = getActivity().getIntent().getStringExtra("username");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                list = DaoUser.getFriends(username);
                list1 = DaoFriend.queryMomentList(username);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        view =inflater.inflate(R.layout.fragment_friends,container,false);
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
                bundle.putString("length",list.get(position).getLength()+"");
                bundle.putString("username",username);
                Intent intent = new Intent(getActivity(), FriendMessage.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String from_name = list1.get(position).getFrom_name();
                String content = list1.get(position).getContent();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("好友申请")
                        .setMessage(from_name+"\n"+content)
                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if("SUCCESS".equals(DaoFriend.addFriend(username,from_name))){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                            list = DaoUser.getFriends(getActivity().getIntent().getStringExtra("username"));
                                            list1 = DaoFriend.queryMomentList(getActivity().getIntent().getStringExtra("username"));
                                            mListView.setAdapter(new FriendsAdapter(getLayoutInflater(),list));
                                            mListView1.setAdapter(new MomentAdapter(getLayoutInflater(),list1));
                                            if(list1.size() == 0){
                                                applicationTextView.setVisibility(View.GONE);
                                            }
                                            if(list.size() == 0){
                                                friendTextView.setVisibility(View.GONE);
                                            }
                                            if (list1.size() == 0 && list.size() == 0){mRelativeLayout.setVisibility(View.VISIBLE);}

                                        }
                                    });
                                }else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),"添加失败",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if("SUCCESS".equals(DaoFriend.updateProcessed(username,from_name))){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),"已拒绝",Toast.LENGTH_SHORT).show();
                                            list1 = DaoFriend.queryMomentList(getActivity().getIntent().getStringExtra("username"));
                                            mListView1.setAdapter(new MomentAdapter(getLayoutInflater(),list1));
                                            if(list1.size() == 0){
                                                applicationTextView.setVisibility(View.GONE);
                                            }
                                            if(list.size() == 0){
                                                friendTextView.setVisibility(View.GONE);
                                            }
                                            if (list1.size() == 0 && list.size() == 0){mRelativeLayout.setVisibility(View.VISIBLE);}
                                        }
                                    });
                                }else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),"网络异常",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }).create();
                builder.show();
            }
        });
    }

    private void initView() {
        applicationTextView = view.findViewById(R.id.application);
        friendTextView = view.findViewById(R.id.friend);
        mRelativeLayout = view.findViewById(R.id.nofriend);
        //find
        mListView=view.findViewById(R.id.listView);
        mListView1 = view.findViewById(R.id.applicationListView);
        //设置适配器
        mListView.setAdapter(new FriendsAdapter(this.getLayoutInflater(),list));
        mListView1.setAdapter(new MomentAdapter(getLayoutInflater(),list1));
        if(list1.size() == 0){
            applicationTextView.setVisibility(View.GONE);
        }
        if(list.size() == 0){
            friendTextView.setVisibility(View.GONE);
        }
        if (list1.size() == 0 && list.size() == 1){mRelativeLayout.setVisibility(View.VISIBLE);}
    }
}
