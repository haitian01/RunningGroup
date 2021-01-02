package com.example.runninggroup.viewAndController;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.fragment.FragmentCardMessage;
import com.example.runninggroup.viewAndController.fragment.FragmentTrail;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityCardRecord extends AppCompatActivity {
    private ImageView backImg;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private String[] tabTitle = {"轨迹", "详情"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_record);
        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        backImg = findViewById(R.id.back);
        mTabLayout = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.viewPager);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new FragmentTrail());
        mFragmentList.add(new FragmentCardMessage());
        mViewPager.setAdapter(new InnerPagerAdapter(getSupportFragmentManager(), 0));


        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });


    }




    class InnerPagerAdapter extends FragmentStatePagerAdapter {


        public InnerPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList == null ? 0 : mFragmentList.size();
        }
    }
}
