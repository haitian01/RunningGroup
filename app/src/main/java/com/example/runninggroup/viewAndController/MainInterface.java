package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
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
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.view.WaringDialogWithTwoButton;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentCard;
import com.example.runninggroup.viewAndController.fragment.FragmentData;
import com.example.runninggroup.viewAndController.fragment.FragmentFriendCircle;
import com.example.runninggroup.viewAndController.fragment.FragmentFriends;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainInterface extends AppCompatActivity implements View.OnClickListener, UserController.UserControllerInterface {
    private ViewPager mViewPager;
    private ImageView mImg_rightSideSetting;
    private ImageView mImg_sideSetting, icon_sport;
    private Button mBtn_exit;
    private ConstraintLayout mPersonalSetting,mRightSetting;
    private LinearLayout mLinearLayout;
    private ListView mListView,mRightList;
    private ImageView personalHead,bigPersonalHead;
    private TextView usernameIn,usernameOut;
    private RadioGroup mRadioGroup;
    private RadioButton dataTab, cardTab, friendTab, circleTab;
    Dialog mDialog;
    int viewPagerNum;
    MyPagerAdapter myPagerAdapter;
    ArrayList<Fragment> fragmentList;
    ArrayList<String> list_Title;
    String username = Cache.user.getUsername();
    UserController mUserController = new UserController(this);
    /**
     * 点击位置信息
     */
    public Point point = new Point();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maininterface);
        initView();
        initEvent();
    }

    /**
     * 点击事件拿到point
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            point.x = (int) ev.getRawX();
            point.y = (int) ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

    //获得头像之后的回调
    @Override
    public void getHeadImgBack(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) {
                    personalHead.setImageDrawable(drawable);
                    mImg_sideSetting.setImageDrawable(drawable);
                }else {
                    personalHead.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
                    mImg_sideSetting.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
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
        viewPagerNum = intent.getIntExtra("viewPagerNum", 0);

        initFragment();


    }




    public void initFragment () {

        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentData());
        fragmentList.add(new FragmentCard());
        fragmentList.add(new FragmentFriends());
        fragmentList.add(new FragmentFriendCircle());
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList, list_Title);
        mViewPager.setAdapter(myPagerAdapter);

        if (mRadioGroup.getCheckedRadioButtonId() == dataTab.getId()) viewPagerNum = 0;
        else if (mRadioGroup.getCheckedRadioButtonId() == cardTab.getId()) viewPagerNum = 1;
        else if (mRadioGroup.getCheckedRadioButtonId() == friendTab.getId()) viewPagerNum = 2;
        else if (mRadioGroup.getCheckedRadioButtonId() == circleTab.getId()) viewPagerNum = 3;
        mViewPager.setCurrentItem(viewPagerNum);



        //设置头像
        //大图
        mDialog = new Dialog(MainInterface.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        personalHead.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        bigPersonalHead.setImageResource(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        mUserController.getHeadImg(ImgNameUtil.getUserHeadImgName(Cache.user.getId()));
        usernameIn.setText(Cache.user.getUsername());
        usernameOut.setText(Cache.user.getUsername());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }

            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mImg_sideSetting = findViewById(R.id.sideSetting);
        mBtn_exit = findViewById(R.id.button_quit);
        mImg_rightSideSetting = findViewById(R.id.rightSightSetting);
        icon_sport = findViewById(R.id.icon_sport);
        mPersonalSetting = findViewById(R.id.personalSetting);
        mRightSetting = findViewById(R.id.rightSetting);
        mLinearLayout = findViewById(R.id.ll_container);
        mListView = findViewById(R.id.personalListView);
        mRightList = findViewById(R.id.rightList);
        personalHead = findViewById(R.id.personalImage);
        usernameOut = findViewById(R.id.username_out);
        usernameIn = findViewById(R.id.username_in);
        mRadioGroup = findViewById(R.id.tabs_rg);
        dataTab = findViewById(R.id.data_tab);
        cardTab = findViewById(R.id.card_tab);
        friendTab = findViewById(R.id.friend_tab);
        circleTab = findViewById(R.id.circle_tab);



        bigPersonalHead = getImageView(getDrawable(Cache.user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w));
        StatusBarUtils.setWindowStatusBarColor(this, R.color.top);
        initFragment();
        setDrawableTop(getResources().getDrawable(R.drawable.img_data_selected), getResources().getDrawable(R.drawable.img_card), getResources().getDrawable(R.drawable.img_friend), getResources().getDrawable(R.drawable.img_circle));

    }


    public void setDrawableTop (Drawable drawableData, Drawable drawableCard,  Drawable drawableFriend, Drawable drawableTeam) {

        RadioButton[] rbs = new RadioButton[4];
        rbs[0] =  (RadioButton) findViewById(R.id.data_tab);
        rbs[1] = (RadioButton)findViewById(R.id.card_tab) ;
        rbs[2] = (RadioButton)findViewById(R.id.friend_tab);
        rbs[3] = (RadioButton)findViewById(R.id.circle_tab);

        //定义底部标签图片大小
        drawableData.setBounds(0, 3, 70, 70);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbs[0].setCompoundDrawables(null, drawableData, null, null);//只放上面

        drawableCard.setBounds(0, 3, 70, 70);
        rbs[1].setCompoundDrawables(null,drawableCard, null, null);

        drawableFriend.setBounds(0, 3, 70,70);
        rbs[2].setCompoundDrawables(null, drawableFriend, null, null);

        drawableTeam.setBounds(0, 3, 70,70);
        rbs[3].setCompoundDrawables(null, drawableTeam, null, null);

    }






    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        mImg_sideSetting.setOnClickListener(this);
        mBtn_exit.setOnClickListener(this);
        mImg_rightSideSetting.setOnClickListener(this);
        personalHead.setOnClickListener(this);
        mLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPersonalSetting.setVisibility(View.INVISIBLE);
                mRightSetting.setVisibility(View.INVISIBLE);
                mLinearLayout.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        icon_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打卡
                final String[] items3 = new String[]{"计时跑步", "手动录入"};//创建item
                android.app.AlertDialog alertDialog3 = new android.app.AlertDialog.Builder(MainInterface.this)
                        .setTitle("选择您的打卡方式")
                        .setIcon(R.drawable.paobu)
                        .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        Intent intent1 = new Intent(MainInterface.this, TimerCard.class);
                                        startActivity(intent1);

                                        break;
                                    case 1:
                                        Intent intent = new Intent(MainInterface.this, CardPersonal.class);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        })
                        .create();
                alertDialog3.show();
            }
        });
        mPersonalSetting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        if (i == 0) setDrawableTop(getResources().getDrawable(R.drawable.img_data_selected), getResources().getDrawable(R.drawable.img_card), getResources().getDrawable(R.drawable.img_friend), getResources().getDrawable(R.drawable.img_circle));
                        else if (i == 1) setDrawableTop(getResources().getDrawable(R.drawable.img_data), getResources().getDrawable(R.drawable.img_card_selected), getResources().getDrawable(R.drawable.img_friend), getResources().getDrawable(R.drawable.img_circle));
                        else if (i == 2) setDrawableTop(getResources().getDrawable(R.drawable.img_data), getResources().getDrawable(R.drawable.img_card), getResources().getDrawable(R.drawable.img_friend_selected), getResources().getDrawable(R.drawable.img_circle));
                        else if (i == 3) setDrawableTop(getResources().getDrawable(R.drawable.img_data), getResources().getDrawable(R.drawable.img_card), getResources().getDrawable(R.drawable.img_friend), getResources().getDrawable(R.drawable.img_circle_selected));
                        if (i == 1) {
                            icon_sport.setVisibility(View.VISIBLE);
                            mImg_rightSideSetting.setVisibility(View.INVISIBLE);
                        }else {
                            icon_sport.setVisibility(View.INVISIBLE);
                            mImg_rightSideSetting.setVisibility(View.VISIBLE);
                        }
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
                    intent.putExtra("viewPagerNum", mViewPager.getCurrentItem());
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
                    case 4 :

                        break;
                }
            }
        });





    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sideSetting:
                mPersonalSetting.setVisibility(View.VISIBLE);
                mLinearLayout.setVisibility(View.VISIBLE);
                //设置头像


                break;
            case R.id.button_quit:
                WaringDialogWithTwoButton waringDialogWithTwoButton = new WaringDialogWithTwoButton(MainInterface.this, "确定退出当前账号吗？", "取消", "确认");
                waringDialogWithTwoButton.setOnButtonClickListener(new WaringDialogWithTwoButton.OnButtonClickListener() {
                    @Override
                    public void right() {
//                        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.clear();
//                        editor.apply();
                        waringDialogWithTwoButton.dismiss();
                        Intent intent = new Intent(MainInterface.this, Login.class);
                        startActivity(intent);
                    }

                    @Override
                    public void left() {
                        waringDialogWithTwoButton.dismiss();
                    }
                });
                waringDialogWithTwoButton.show();
                break;
            case R.id.rightSightSetting:
                if (mRightSetting.getVisibility() == View.INVISIBLE && mLinearLayout.getVisibility() == View.INVISIBLE) {
                    mRightSetting.setVisibility(View.VISIBLE);
                    mLinearLayout.setVisibility(View.VISIBLE);
                }else {
                    mRightSetting.setVisibility(View.INVISIBLE);
                    mLinearLayout.setVisibility(View.INVISIBLE);
                }
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
        else iv.setImageResource(R.drawable.img_unload);
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
                        WaringDialogWithTwoButton waringDialogWithTwoButton = new WaringDialogWithTwoButton(MainInterface.this, "尚未加入任何跑团", "去加入", "去创建");
                        waringDialogWithTwoButton.setOnButtonClickListener(new WaringDialogWithTwoButton.OnButtonClickListener() {
                            @Override
                            public void right() {
                                waringDialogWithTwoButton.dismiss();
                                Intent intent = new Intent(MainInterface.this, GroupBuild.class);
                                startActivity(intent);
                            }

                            @Override
                            public void left() {
                                waringDialogWithTwoButton.dismiss();
                                Intent intent = new Intent(MainInterface.this, SearchActivity.class);
                                startActivity(intent);
                            }
                        });
                       waringDialogWithTwoButton.show();
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
