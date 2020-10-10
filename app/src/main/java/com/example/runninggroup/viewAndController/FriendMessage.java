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
import android.widget.Button;
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
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.util.ConstantUtil;
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

public class FriendMessage extends AppCompatActivity implements UserController.UserControllerInterface, View.OnClickListener, FriendRelationController.FriendRelationControllerInterface {

    TextView aliasText,registerNumText,settingText;
    ImageView mImageView, backImg;
    private String alias;
    private UserController mUserController = new UserController(this);
    private FriendRelationController mFriendRelationController = new FriendRelationController(this);
    private ListView mListView;
    private Button addFriend;
    private int fromActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendmessage);
        initView();
        initEvent();
    }

    private void initView() {
        alias = getIntent().getStringExtra("alias");
        fromActivity = getIntent().getIntExtra("fromActivity", ConstantUtil.MAIN_INTERFACE);
       aliasText = findViewById(R.id.alias);
       registerNumText = findViewById(R.id.registerNum);
       backImg = findViewById(R.id.back_img);
       addFriend = findViewById(R.id.add_friend);
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
        if (Cache.friend.getId() != Cache.user.getId()) {
            mFriendRelationController.isMyFriend();
        }



        setView();
    }
    private void initEvent() {
        mImageView.setImageResource(Cache.friend.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        backImg.setOnClickListener(this);
        settingText.setOnClickListener(this);
        addFriend.setOnClickListener(this);


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
                Intent intent = null;
                if (fromActivity == ConstantUtil.MAIN_INTERFACE) {
                    intent = new Intent(FriendMessage.this, MainInterface.class);
                    intent.putExtra("id",2);
                }
                else if (fromActivity == ConstantUtil.PERSON_SEARCH) {
                    intent = new Intent(FriendMessage.this, SearchActivity.class);
                }

                startActivity(intent);
                break;
            case R.id.setting:
                break;

            case R.id.add_friend:
                Intent intent1 = new Intent(getApplicationContext(), AddFriendActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void isMyFriendBack(List<FriendRelation> friendRelationList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendRelationList == null)
                    Toast.makeText(FriendMessage.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (friendRelationList.size() == 0)  {
                    addFriend.setVisibility(View.VISIBLE);
                }
                else {
                    if (friendRelationList.get(0).getAlias() != null) aliasText.setText(friendRelationList.get(0).getAlias());
                 }
            }
        });
    }
}
