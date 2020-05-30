package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentCard;
import com.example.runninggroup.viewAndController.fragment.FragmentData;
import com.example.runninggroup.viewAndController.fragment.FragmentFriends;
import com.example.runninggroup.viewAndController.fragment.FragmentGroup;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainInterface extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Button mBtn_sideSetting,mBtn_rightSideSetting;
    private Button mBtn_exit;
    private ConstraintLayout mPesonalSetting,mRightSetting;
    private LinearLayout mLineraLayout;
    private ListView mListView,mRightList;
    private ImageView personalHead,bigPersonalHead;
    private TextView usernameText;
    String username;
    Dialog mDialog;
    int id;

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
        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id",0);
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mBtn_sideSetting = findViewById(R.id.sideSetting);
        mBtn_exit = findViewById(R.id.button_quit);
        mBtn_rightSideSetting = findViewById(R.id.rightSightSetting);
        mPesonalSetting = findViewById(R.id.personalSetting);
        mRightSetting = findViewById(R.id.rightSetting);
        mLineraLayout = findViewById(R.id.ll_container);
        mListView = findViewById(R.id.personalListView);
        mRightList = findViewById(R.id.rightList);
        personalHead = findViewById(R.id.personalImage);
        usernameText = findViewById(R.id.text_userName);

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
        usernameText.setText(username);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList,list_Title));
        mTabLayout.setupWithViewPager(mViewPager);//此方法就是让tablayout和ViewPager联动
        mViewPager.setCurrentItem(id);
        //大图
        mDialog = new Dialog(MainInterface.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        bigPersonalHead = getImageView();
        mDialog.setContentView(bigPersonalHead);


    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        mBtn_sideSetting.setOnClickListener(this);
        mBtn_exit.setOnClickListener(this);
        mBtn_rightSideSetting.setOnClickListener(this);
        personalHead.setOnClickListener(this);
        mLineraLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPesonalSetting.setVisibility(View.INVISIBLE);
                mRightSetting.setVisibility(View.INVISIBLE);
                mLineraLayout.setVisibility(View.GONE);
                return false;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0){
                    Intent intent = new Intent(MainInterface.this, PersonalSetting.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }else if (id == 1){
                    Toast.makeText(MainInterface.this, "该功能暂未上线", Toast.LENGTH_SHORT).show();
                }else if (id == 2){
                    Toast.makeText(MainInterface.this, "该功能暂未上线", Toast.LENGTH_SHORT).show();
                }else if (id == 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainInterface.this);
                    builder.setTitle("客服联系方式：");
                    builder.setMessage("客服联系邮箱：3448562305@qq.com");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainInterface.this, "期待您的联系与建议", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            }
        });
        mRightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        android.app.AlertDialog.Builder alertDialog3 = new android.app.AlertDialog.Builder(MainInterface.this);
                        final View view1=getLayoutInflater().inflate(R.layout.helper_addfriend,null);
                        alertDialog3.setView(view1)
                        .setTitle("添加好友")
                        .setIcon(R.mipmap.ic_launcher).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText friend_name = view1.findViewById(R.id.friend_name);
                                final EditText content = view1.findViewById(R.id.content);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(DaoFriend.queryFriend(username,friend_name.getText().toString())){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MainInterface.this,"你们已经是好友啦！",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else {

                                            String result = DaoFriend.insertMoment(username,friend_name.getText().toString(),content.getText().toString());
                                            if("SUCCESS".equals(result)){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(MainInterface.this,"请求发送成功",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(MainInterface.this,"请求发送失败",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }

                                    }
                                }).start();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainInterface.this,"点击1",Toast.LENGTH_SHORT).show();
                            }
                        }).create();


                        alertDialog3.show();

                        break;
                    case 1:
                        Intent intent = new Intent(MainInterface.this,Write.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                }
            }
        });
        bigPersonalHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sideSetting:
                mPesonalSetting.setVisibility(View.VISIBLE);
                mLineraLayout.setVisibility(View.VISIBLE);
                //设置头像
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        final Drawable drawable = DaoUser.getImg(DaoUser.getUserHeadImgName(username));
                        final Drawable drawable = DaoUser.getImg(DaoUser.getUserHeadImgName(username));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(drawable != null) personalHead.setImageDrawable(drawable);
                                else personalHead.setImageResource(R.mipmap.defaultpic);
                            }
                        });
                    }
                }).start();

                break;
            case R.id.button_quit:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainInterface.this);
                builder.setTitle("退出登录提示：");
                builder.setMessage("您确定要退出登录状态，并返回到登录界面吗？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainInterface.this, Login.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainInterface.this, "运动就是坚持！！", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;
            case R.id.rightSightSetting:
                mRightSetting.setVisibility(View.VISIBLE);
                mLineraLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.personalImage:
                mDialog.show();
                break;
        }
    }
    //动态图片
    private ImageView getImageView(){
        ImageView iv = new ImageView(MainInterface.this);
        //宽高
        iv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //设置Padding
        iv.setPadding(20,20,20,20);
        //imageView设置图片
        iv.setImageDrawable(DaoUser.getImg(DaoUser.getUserHeadImgName(username)));
        return iv;
    }





}
