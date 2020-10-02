package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.viewAndController.adapter.DynamicAdapter;
import com.example.runninggroup.viewAndController.adapter.FriendMessageAdapter;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentDynamic;
import com.example.runninggroup.viewAndController.fragment.FragmentFriendManage;
import com.example.runninggroup.viewAndController.fragment.FragmentPersonalCard;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FriendMessage extends AppCompatActivity implements UserController.UserControllerInterface, View.OnClickListener {

    TextView aliasText,registerNumText,settingText;
    ImageView mImageView, backImg;
    private String alias;
    private UserController mUserController = new UserController(this);
    private ListView mListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendmessage);
        initView();
        initEvent();
    }

    private void initView() {
        alias = getIntent().getStringExtra("alias");
       aliasText = findViewById(R.id.alias);
       registerNumText = findViewById(R.id.registerNum);
       backImg = findViewById(R.id.back_img);
        settingText = findViewById(R.id.setting);
        mImageView = findViewById(R.id.img);
        mListView = findViewById(R.id.friend_message);
        mListView.setAdapter(new FriendMessageAdapter(getLayoutInflater(), Cache.friend));
        aliasText.setText(alias == null ? Cache.friend.getUsername() : alias);
        registerNumText.setText(Cache.friend.getRegisterNum() + "(" + Cache.friend.getUsername() + ")");
        //透明
        StatusBarUtils.setStatusBarFullTransparent(this);
        String temp =Cache.friend.getHeadImg();
        mUserController.getHeadImg(Cache.friend.getHeadImg());

        setView();
    }
    private void initEvent() {
        backImg.setOnClickListener(this);
        settingText.setOnClickListener(this);

    }
    private void setView(){



    }

    @Override
    public void getHeadImg(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null)
                mImageView.setImageDrawable(drawable);
                else Toast.makeText(FriendMessage.this, "图片为空", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                Intent intent = new Intent(FriendMessage.this, MainInterface.class);
                intent.putExtra("id",2);
                startActivity(intent);
                break;
            case R.id.setting:
                break;
        }
    }
}
