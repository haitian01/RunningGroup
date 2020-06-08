package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.adapter.DynamicAdapter;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentDynamic;
import com.example.runninggroup.viewAndController.fragment.FragmentFriendManage;
import com.example.runninggroup.viewAndController.fragment.FragmentPersonalCard;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FriendMessage extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    TextView nameText,groupText,lengthText;
    ListView mFriendMenuList;
    ImageView mImageView;
    Intent mIntent;
    String name;
    String group;
    String length;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendmessage);
        initView();
        initEvent();
    }

    private void initView() {
        username = getIntent().getStringExtra("username");
        mIntent = getIntent();
        name = mIntent.getStringExtra("name");
        group = mIntent.getStringExtra("group");
        length = mIntent.getStringExtra("length");
        nameText = findViewById(R.id.name);
        groupText = findViewById(R.id.group);
        lengthText = findViewById(R.id.runNum);
        mFriendMenuList = findViewById(R.id.friend_menu);
        mImageView = findViewById(R.id.img);
        mTabLayout = findViewById(R.id.manage_tbl);
        mViewPager = findViewById(R.id.manage_vpg);
        ArrayList<Fragment> list = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        list.add(new FragmentDynamic());
        list.add(new FragmentPersonalCard());
        if(! name.equals(username)){
            list.add(new FragmentFriendManage());
        }
        list1.add("动态");
        list1.add("打卡");
        if(! name.equals(username)){
            list1.add("管理");
        }
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),list,list1));
        mTabLayout.setupWithViewPager(mViewPager);
        setView();
    }
    private void initEvent() {
        Toast.makeText(FriendMessage.this,name+"",Toast.LENGTH_SHORT).show();
        mFriendMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(FriendMessage.this, FragmentFriendManage.class);
                        intent.putExtra("username",username);
                        intent.putExtra("name",name);
                        intent.putExtra("group",group);
                        intent.putExtra("length",length);
                        startActivity(intent);
                }
            }
        });

    }
    private void setView(){
        nameText.setText(name);
        groupText.setText(group);
        lengthText.setText(length);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = DaoUser.getImg(DaoUser.getUserHeadImgName(name));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null) mImageView.setImageDrawable(drawable);
                        else mImageView.setImageResource(R.mipmap.defaultpic);
                    }
                });
            }
        }).start();


    }
}
