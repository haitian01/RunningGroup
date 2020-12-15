package com.example.runninggroup.viewAndController;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentPersonSearch;
import com.example.runninggroup.viewAndController.fragment.FragmentTeamSearch;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, UserController.UserControllerInterface {
    private ImageView backImg;
    RadioButton personBtn,teamBtn;
    RadioGroup tabGroup;
    ViewPager mViewPager;
    List<Fragment> frgList;
    private UserController mUserController = new UserController(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initEvent();
    }

    private void initEvent() {
        tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.team_tab) {
                    teamBtn.setBackground(getDrawable(R.drawable.search_activity_right_btn_checked));
                    personBtn.setBackground(getDrawable(R.drawable.search_activity_left_btn_unchecked));
                    mViewPager.setCurrentItem(1);
                }else {
                    personBtn.setBackground(getDrawable(R.drawable.search_activity_left_btn_checked));
                    teamBtn.setBackground(getDrawable(R.drawable.search_activity_right_btn_unchecked));
                    mViewPager.setCurrentItem(0);
                }
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton btn = (RadioButton) tabGroup.getChildAt(position);
                btn.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });


    }

    private void initView() {
        personBtn = findViewById(R.id.person_tab);
        teamBtn = findViewById(R.id.team_tab);
        tabGroup = findViewById(R.id.tabs_rg);
        mViewPager = findViewById(R.id.viewPager);
        backImg = findViewById(R.id.back);

        frgList = new ArrayList<>();
        frgList.add(new FragmentPersonSearch());
        frgList.add(new FragmentTeamSearch());
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) frgList, null));


        StatusBarUtils.setWindowStatusBarColor(this,R.color.top);




    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = new User();
        user.setId(Cache.user.getId());
        mUserController.selectUserByUser(user);


    }

    @Override
    public void selectUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(SearchActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() == 0)
                    Toast.makeText(SearchActivity.this, "程序出错", Toast.LENGTH_SHORT).show();
                else  Cache.user = users.get(0);
            }
        });
    }
}
