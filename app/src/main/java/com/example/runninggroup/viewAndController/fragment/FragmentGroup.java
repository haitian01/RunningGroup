package com.example.runninggroup.viewAndController.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.GroupBuild;
import com.example.runninggroup.viewAndController.GroupIntroduct;
import com.example.runninggroup.viewAndController.GroupMessage;
import com.example.runninggroup.viewAndController.adapter.FriendsAdapter;
import com.example.runninggroup.viewAndController.adapter.GroupAdapter;
import com.example.runninggroup.viewAndController.adapter.SearchGroupAdapter;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentGroup extends Fragment implements View.OnClickListener {
    public ListView mListView,mListView1;
    public List<GroupHelper> list;
    public List<GroupHelper> list_serch;
    public GroupAdapter mAdapter;
    EditText mEditText;
    ImageView mImageView;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                list = DaoGroup.getGroups();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        mListView1 = view.findViewById(R.id.search_result);
        mEditText = view.findViewById(R.id.group_search);
    }
    private void initEvent() {
        mImageView.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("group",list.get(position).getGroupName());
                bundle.putString("num",list.get(position).getNumbers()+"");
                bundle.putString("leader",list.get(position).getLeaderName());
                bundle.putString("username",getActivity().getIntent().getStringExtra("username"));
                Intent intent = new Intent(getActivity(), GroupIntroduct.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("group",list_serch.get(position).getGroupName());
                bundle.putString("num",list_serch.get(position).getNumbers()+"");
                bundle.putString("leader",list_serch.get(position).getLeaderName());
                bundle.putString("username",getActivity().getIntent().getStringExtra("username"));
                mEditText.setText(null);
                mListView1.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), GroupIntroduct.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = mEditText.getText().toString();
                list_serch = new ArrayList<>();
                for (GroupHelper groupHelper : list) {
                    if(groupHelper.getGroupName().contains(content) && !"".equals(content)){
                       list_serch.add(groupHelper);
                    }
                }
                if (list_serch.size() != 0){
                    mListView1.setVisibility(View.VISIBLE);
                    mListView1.setAdapter(new SearchGroupAdapter(getLayoutInflater(),list_serch));
                }
                else mListView1.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = getActivity().getIntent().getStringExtra("username");
                        if("".equals(DaoUser.getMyGroup(username))){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                                            .setTitle("提示")
                                            .setMessage("您还没有参加任何跑团，是否要建立自己的跑团？")
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getActivity(), GroupBuild.class);
                                                    intent.putExtra("username",getActivity().getIntent().getStringExtra("username"));
                                                    startActivityForResult(intent,1);
                                                }
                                            })

                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                }
                                            })
                                            .create();
                                    alertDialog2.show();
                                }
                            });

                        }else {
                            Intent intent = new Intent(getContext(),GroupMessage.class);
                            intent.putExtra("username",getActivity().getIntent().getStringExtra("username"));
//                            intent.putExtra("group",DaoUser.getMyGroup(getActivity().getIntent().getStringExtra("username")));
                            startActivity(intent);
                        }
                    }
                }).start();



                break;

        }
    }
}
