package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentCard;
import com.example.runninggroup.viewAndController.fragment.FragmentData;
import com.example.runninggroup.viewAndController.fragment.FragmentFriends;
import com.example.runninggroup.viewAndController.fragment.FragmentGroup;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainInterface extends AppCompatActivity implements View.OnClickListener, UserController.UserControllerInterface {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mImg_rightSideSetting;
    private ImageView mImg_sideSetting;
    private Button mBtn_exit;
    private ConstraintLayout mPesonalSetting,mRightSetting;
    private LinearLayout mLineraLayout;
    private ListView mListView,mRightList;
    private ImageView personalHead,bigPersonalHead;
    private TextView usernameIn,usernameOut;
    private RadioGroup mRadioGroup;
    Dialog mDialog;
    int id;
    MyPagerAdapter myPagerAdapter;
    ArrayList<Fragment> fragmentList;
    ArrayList<String> list_Title;
    String username = Cache.user.getUsername();
    UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maininterface);
        initView();
        initEvent();
    }
    //获得头像之后的回调
    @Override
    public void getHeadImg(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) {
                    personalHead.setImageDrawable(drawable);
                    mImg_sideSetting.setImageDrawable(drawable);
                    Toast.makeText(MainInterface.this, "头像获取成功", Toast.LENGTH_SHORT).show();
                }else {
                    personalHead.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
                    mImg_sideSetting.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
                    Toast.makeText(MainInterface.this, "头像获取失败", Toast.LENGTH_SHORT).show();
                }
                //大图
                bigPersonalHead = getImageView(drawable);
                mDialog.setContentView(bigPersonalHead);
                bigPersonalHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        id = intent.getIntExtra("id",0);
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentData());
        fragmentList.add(new FragmentCard());
        fragmentList.add(new FragmentFriends());
        fragmentList.add(new FragmentGroup());
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(id);

        //设置头像
        personalHead.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        bigPersonalHead.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        mUserController.getHeadImg(Cache.user.getHeadImg());
        usernameIn.setText(Cache.user.getUsername());
        usernameOut.setText(Cache.user.getUsername());

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initView() {
        id = getIntent().getIntExtra("id",0);
        mViewPager = findViewById(R.id.viewPager);
        mImg_sideSetting = findViewById(R.id.sideSetting);
        mBtn_exit = findViewById(R.id.button_quit);
        mImg_rightSideSetting = findViewById(R.id.rightSightSetting);
        mPesonalSetting = findViewById(R.id.personalSetting);
        mRightSetting = findViewById(R.id.rightSetting);
        mLineraLayout = findViewById(R.id.ll_container);
        mListView = findViewById(R.id.personalListView);
        mRightList = findViewById(R.id.rightList);
        personalHead = findViewById(R.id.personalImage);
        usernameOut = findViewById(R.id.username_out);
        usernameIn = findViewById(R.id.username_in);
        mRadioGroup = findViewById(R.id.tabs_rg);
        fragmentList = new ArrayList<>();
//        list_Title = new ArrayList<>();
        fragmentList.add(new FragmentData());
        fragmentList.add(new FragmentCard());
        fragmentList.add(new FragmentFriends());
        fragmentList.add(new FragmentGroup());
        StatusBarUtils.setWindowStatusBarColor(this, R.color.top);
//        setDrawableTop ();
//        list_Title.add("数据");
//        list_Title.add("打卡");
//        list_Title.add("好友");
//        list_Title.add("跑团");
        usernameOut.setText(Cache.user.getUsername());
        usernameIn.setText(Cache.user.getUsername());
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList, list_Title);
        mViewPager.setAdapter(myPagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);//此方法就是让tablayout和ViewPager联动
        mViewPager.setCurrentItem(id);
        //大图
        mDialog = new Dialog(MainInterface.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        personalHead.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        bigPersonalHead = getImageView(getDrawable(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w));
        mUserController.getHeadImg(Cache.user.getHeadImg());
    }

    public void setDrawableTop () {

        RadioButton[] rbs = new RadioButton[4];
        rbs[0] =  (RadioButton) findViewById(R.id.data_tab);
        rbs[1] = (RadioButton)findViewById(R.id.card_tab) ;
        rbs[2] = (RadioButton)findViewById(R.id.friend_tab);
        rbs[3] = (RadioButton)findViewById(R.id.group_tab);
        for (RadioButton rb : rbs) {
            //给每个RadioButton加入drawable限制边距控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect rt = new Rect(0, 0, drawables[1].getMinimumWidth()*2/3, drawables[1].getMinimumHeight()*2/3);
            //定义一个Rect边界
            drawables[1].setBounds(rt);

            //添加限制给控件
            rb.setCompoundDrawables(null,drawables[1],null,null);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        mImg_sideSetting.setOnClickListener(this);
        mBtn_exit.setOnClickListener(this);
        mImg_rightSideSetting.setOnClickListener(this);
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
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        mViewPager.setCurrentItem(i);
                        return;
                    }
                }
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(position);
                radioButton.setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0){
                    Intent intent = new Intent(MainInterface.this, PersonalSetting.class);
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
                            Intent intent = new Intent(MainInterface.this, SearchActivity.class);
                            startActivity(intent);
//                        android.app.AlertDialog.Builder alertDialog3 = new android.app.AlertDialog.Builder(MainInterface.this);
//                        final View view1=getLayoutInflater().inflate(R.layout.helper_addfriend,null);
//                        alertDialog3.setView(view1)
//                        .setTitle("添加好友")
//                        .setIcon(R.drawable.add_account).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                final EditText friend_name = view1.findViewById(R.id.friend_name);
//                                final EditText content = view1.findViewById(R.id.content);
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if(DaoFriend.queryFriend(username,friend_name.getText().toString())){
//                                           makeToast("你们已经是好友啦");
//                                        }else {
//
//                                            String result = DaoFriend.insertMoment(username,friend_name.getText().toString(),content.getText().toString());
//                                            if("SUCCESS".equals(result)){
//                                               makeToast("请求发送成功！");
//                                            }else {
//                                                makeToast("请求发送失败");
//                                            }
//                                        }
//
//                                    }
//                                }).start();
//
//                            }
//                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainInterface.this,"期待您拥有更值得拥有的跑友",Toast.LENGTH_SHORT).show();
//                            }
//                        }).create();
//
//
//                        alertDialog3.show();

                        break;
                    //发表动态
                    case 1:
                        intent = new Intent(MainInterface.this, Write.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        break;
                    //好友申请
                    case 2:
                        intent = new Intent(MainInterface.this, FriendApplicationActivity.class);
                        startActivity(intent);
                        break;
                    //我的跑团
                    case 3:
                        User user = new User();
                        user.setId(Cache.user.getId());
                        mUserController.selectUserByUser(user);
                        break;
                }
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


                break;
            case R.id.button_quit:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainInterface.this);
                builder.setTitle("退出登录提示：");
                builder.setMessage("您确定要退出登录状态，并返回到登录界面吗？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.apply();
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
    private ImageView getImageView(Drawable drawable){
        ImageView iv = new ImageView(MainInterface.this);
        //宽高
        iv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //设置Padding
        iv.setPadding(20,20,20,20);
        //imageView设置图片
        if (drawable != null)
        iv.setImageDrawable(drawable);
        else iv.setImageResource(R.mipmap.defaultpic);
        return iv;
    }
    private void makeToast(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainInterface.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null) Toast.makeText(MainInterface.this, "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    Cache.user = users.get(0);
                    if (Cache.user.getTeam() == null) {
                        android.app.AlertDialog.Builder alertDialog3 = new android.app.AlertDialog.Builder(MainInterface.this);
                        alertDialog3.setMessage("尚未加入跑团").setPositiveButton("去加入", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainInterface.this, SearchActivity.class);
                                intent.putExtra("id", 1);
                                startActivity(intent);
                            }
                        }).setNegativeButton("去创建", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainInterface.this, GroupBuild.class);
                                startActivity(intent);
                            }
                        }).create().show();
                    }else {
                        Cache.team = Cache.user.getTeam();
                        Intent intent1 = new Intent(MainInterface.this, TeamIntroduction.class);
                        startActivity(intent1);
                    }
                }
            }
        });
    }
}
