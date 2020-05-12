package com.example.runninggroup.viewAndController;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentCard;
import com.example.runninggroup.viewAndController.fragment.FragmentData;
import com.example.runninggroup.viewAndController.fragment.FragmentFriends;
import com.example.runninggroup.viewAndController.fragment.FragmentGroup;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainInterface extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maininterface);
        initView();
        initEvent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        ArrayList<String> list_Title = new ArrayList<>();
        fragmentList.add(new FragmentData());
        fragmentList.add(new FragmentCard());
        fragmentList.add(new FragmentFriends());
        fragmentList.add(new FragmentGroup());
        list_Title.add("数据");
        list_Title.add("打卡");
        list_Title.add("好友");
        list_Title.add("跑团");
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList,list_Title));
        mTabLayout.setupWithViewPager(mViewPager);//此方法就是让tablayout和ViewPager联动
        Intent intent = getIntent();
        int jump = intent.getIntExtra("jump",0);
        mViewPager.setCurrentItem(jump);

    }
    private void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
