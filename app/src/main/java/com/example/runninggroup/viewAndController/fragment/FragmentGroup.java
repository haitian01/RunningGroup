package com.example.runninggroup.viewAndController.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.GroupBuild;
import com.example.runninggroup.viewAndController.GroupMessage;
import com.example.runninggroup.viewAndController.adapter.FriendsAdapter;
import com.example.runninggroup.viewAndController.adapter.GroupAdapter;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentGroup extends Fragment implements View.OnClickListener {
    public ListView mListView;
    public List<GroupHelper> list;
    public GroupAdapter mAdapter;
    ImageView mImageView;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list=new ArrayList<>();

        list.add(new GroupHelper(R.mipmap.friendshelper,"北邮跑团","20人"));
        list.add(new GroupHelper(R.mipmap.friendshelper,"北航跑团","10人"));
        list.add(new GroupHelper(R.mipmap.friendshelper,"北师跑团","30人"));
        list.add(new GroupHelper(R.mipmap.friendshelper,"清华跑团","50人"));
        list.add(new GroupHelper(R.mipmap.friendshelper,"北大跑团","27人"));
        view =inflater.inflate(R.layout.fragment_group,container,false);
        //find
        mListView=view.findViewById(R.id.listView);
        //初始化适配器
        mAdapter=new GroupAdapter(this.getLayoutInflater(),list);
        //设置适配器
        mListView.setAdapter(mAdapter);

        initView();
        initEvent();
        return view;
    }

    private void initView() {
        mImageView = view.findViewById(R.id.img);
    }
    private void initEvent() {
        mImageView.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("group",list.get(position).getName());
                bundle.putString("num",list.get(position).getNum());
                Intent intent = new Intent(getActivity(), GroupMessage.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img:
                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("您还没有参加任何跑团，是否要建立自己的跑团？")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), GroupBuild.class);
                                startActivityForResult(intent,1);
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(), "这是取消按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog2.show();

                break;
        }
    }
}
