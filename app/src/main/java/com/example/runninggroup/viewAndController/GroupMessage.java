package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentCard;
import com.example.runninggroup.viewAndController.fragment.FragmentData;
import com.example.runninggroup.viewAndController.fragment.FragmentFriends;
import com.example.runninggroup.viewAndController.fragment.FragmentGroup;
import com.example.runninggroup.viewAndController.fragment.FragmentGroupMember;
import com.example.runninggroup.viewAndController.fragment.FragmentGroupTask;
import com.example.runninggroup.viewAndController.helper.GroupHelper;
import com.example.runninggroup.viewAndController.helper.MemberManageHelper;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Member;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

public class GroupMessage extends AppCompatActivity implements View.OnClickListener {
    TextView nameText,groupText,numText,manageText;
    Button mButton;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    ImageView groupImg;
    String username;
    String group;
    String num;
    String leader;
    Drawable mDrawable;
    int admin;
    int id;
    ArrayList<Fragment> fragmentList;
    ArrayList<String> list_Title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupmessage);
        initView();
        initEvent();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        id = intent.getIntExtra("id",0);
        username = intent.getStringExtra("username");

        FragmentGroupTask fragmentGroupTask = new FragmentGroupTask();
        FragmentGroupMember fragmentGroupMember = new FragmentGroupMember();
        Bundle budle=new Bundle();
        budle.putString("group",group);
        fragmentGroupTask.setArguments(budle);
        fragmentGroupMember.setArguments(budle);

        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentGroupMember);
        fragmentList.add(fragmentGroupTask);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList,list_Title));
        mViewPager.setCurrentItem(id);

    }

    private void initView() {
        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id",0);



        nameText = findViewById(R.id.leaderName);
        groupText = findViewById(R.id.group);
        numText = findViewById(R.id.num);
        manageText = findViewById(R.id.manage);
        mButton = findViewById(R.id.join);
        groupImg = findViewById(R.id.img);

        mViewPager = findViewById(R.id.groupmessage_viewPager);
        mTabLayout = findViewById(R.id.groupmessage_tabLayout);
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();


        //访问服务器
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<GroupHelper> list = DaoUser.getMyGroupAll(username);
                if(list.size() != 0){
                    group = list.get(0).getGroupName();
                    mDrawable = DaoUser.getImg(DaoUser.getGroupHeadImgName(group));
                    num = list.get(0).getNumbers()+"";
                    leader = list.get(0).getLeaderName();
                    group = list.get(0).getGroupName();
                    groupText.setText(group);
                    numText.setText(num);
                    nameText.setText(leader);
                }

                Thread m = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<MemberManageHelper> list1 = DaoGroup.getMemberTitle(group);
                        for(MemberManageHelper memberManageHelper:list1){
                            if(memberManageHelper.getUsername().equals(username)){admin=memberManageHelper.getAdmin();}
                        }
                    }
                });
                m.start();

                try {
                    m.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(leader.equals(username)){
                    mButton.setVisibility(View.GONE);
                }else {
                    if(admin == 0){
                        manageText.setVisibility(View.GONE);
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
        if (mDrawable != null) groupImg.setImageDrawable(mDrawable);
        //Activity向 FragmentGroupTask传递group信息
        FragmentGroupTask fragmentGroupTask = new FragmentGroupTask();
        FragmentGroupMember fragmentGroupMember = new FragmentGroupMember();
        Bundle budle=new Bundle();
        budle.putString("group",group);
        fragmentGroupTask.setArguments(budle);
        fragmentGroupMember.setArguments(budle);



        fragmentList.add(fragmentGroupMember);
        fragmentList.add(fragmentGroupTask);
        list_Title.add("Members");
        list_Title.add("Tasks");
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList,list_Title));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(id);

    }
    private void initEvent() {
        //加入、退出按钮点击事件
        mButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.join:
                if(mButton.getText().toString().equals("加入")){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DaoUser.getMyGroup(username);

                            if("".equals(result)){
                                if(DaoUser.setMyGroup(username,group,1,"JOIN").equals("SUCCESS")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mButton.setText("退出");
                                            int number = Integer.parseInt(numText.getText().toString())+1;
                                            numText.setText(number+"");
                                        }
                                    });
                                    Looper.prepare();
                                    Toast.makeText(GroupMessage.this,"成功！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();

                                }else {
                                    Looper.prepare();
                                    Toast.makeText(GroupMessage.this,"失败！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }else {
                                Looper.prepare();
                                Toast.makeText(GroupMessage.this,"失败！\n您已经加入过跑团",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();

                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(DaoUser.setMyGroup(username,group,-1,"OUT").equals("SUCCESS")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mButton.setText("加入");
                                        int number = Integer.parseInt(numText.getText().toString())-1;
                                        numText.setText(number+"");
                                        Toast.makeText(GroupMessage.this,"成功",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Intent intent = new Intent(GroupMessage.this,MainInterface.class);
                                intent.putExtra("username",username);
                                intent.putExtra("id",3);
                                startActivity(intent);

                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GroupMessage.this,"失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
                break;

            case R.id.manage:
                Intent intent = new Intent(GroupMessage.this,Manage.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                bundle.putString("group",group);
                bundle.putString("num",num);
                bundle.putString("leader",leader);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }


    }
}
