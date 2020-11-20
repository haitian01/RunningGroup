package com.example.runninggroup.viewAndController;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.User;

import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.MailSend;
import com.example.runninggroup.util.MyLinkedHashMapUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.viewAndController.adapter.LoginAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Login extends AppCompatActivity implements View.OnClickListener, UserController.UserControllerInterface {
    public static final String TAG = "MyActivityTest";
    private EditText mEditText1,mEditText2;
    private TextView registerTxt,forgetPwdTxt;
    private Button loginBtn;
    private UserController mUserController = new UserController(this);
    RelativeLayout parentRela, bottomParentRela;
    ListView userList;
    ImageView downImg, headImg;
    Set<String> keySet = new HashSet<>();
    private Intent mIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
        .detectDiskWrites()
        .detectAll()// or .detectAll() for all detectable problems                   
        .penaltyLog()
        .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());


        initView();
        initEvent();
    }

    private void initView() {
        mEditText1=findViewById(R.id.registerNum);
        mEditText2=findViewById(R.id.password);
        registerTxt=findViewById(R.id.register);
        loginBtn=findViewById(R.id.login);
        parentRela = findViewById(R.id.parent);
        bottomParentRela = findViewById(R.id.bottomParent);
        forgetPwdTxt=findViewById(R.id.forgetPassword);
        downImg = findViewById(R.id.down);
        headImg = findViewById(R.id.head);
        userList = findViewById(R.id.userList);





        //

//        if (registerNum != null && password != null) {
//            mUserController.isLoad(registerNum, password);
//        }
        mEditText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }



    private void initEvent() {
        registerTxt.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgetPwdTxt.setOnClickListener(this);
        downImg.setOnClickListener(this);
        parentRela.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                parentRela.getWindowVisibleDisplayFrame(r);
                int screenHeight = parentRela.getRootView()
                        .getHeight();
                int heightDifference = screenHeight - (r.bottom);
                if (heightDifference > 200) {
                    //软键盘显示
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.ALIGN_TOP, loginBtn.getId());
                    layoutParams.setMargins(0, 180, 0, 0);
                    bottomParentRela.setLayoutParams(layoutParams);

                } else {
                    //软键盘隐藏
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    layoutParams.setMargins(0, 0, 0, 50);
                    bottomParentRela.setLayoutParams(layoutParams);

                }
            }
        });
        mEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //查找图片
                String temp = mEditText1.getText().toString();
                if (temp != null && temp.length() == 10) mUserController.getImgByRegisterNum(temp);
                else headImg.setImageDrawable(null);

            }
        });
        mEditText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    userList.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                    bottomParentRela.setVisibility(View.VISIBLE);
                }
            }
        });
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView registerNumTxt = view.findViewById(R.id.registerNum);
                String registerNum = registerNumTxt.getText().toString();
                SharedPreferences sp = getSharedPreferences("login", 0);
                String password = sp.getString(registerNum, "");
                mEditText1.setText(registerNum);
                mEditText2.setText(password);
                userList.setVisibility(View.INVISIBLE);
                loginBtn.setVisibility(View.VISIBLE);
                bottomParentRela.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                Intent intent = new Intent(this,Register.class);
                startActivity(intent);
                break;
            case R.id.login:
                String registerNum = mEditText1.getText().toString();
                String password = mEditText2.getText().toString();
                if (StringUtil.isStringNull(registerNum) || StringUtil.isStringNull(password)) {
                    Toast.makeText(this, "输入不合法", Toast.LENGTH_SHORT).show();
                }else {

                    mUserController.isLoad(registerNum, password);

                }
                break;
            case R.id.down:
                if (userList.getVisibility() == View.VISIBLE) {
                    userList.setVisibility(View.INVISIBLE);

                    loginBtn.setVisibility(View.VISIBLE);
                    bottomParentRela.setVisibility(View.VISIBLE);
                }
                else {
                    userList.setVisibility(View.VISIBLE);
                    userList.setAdapter(new LoginAdapter(keySet, this));
                    mEditText1.clearFocus();

                    loginBtn.setVisibility(View.INVISIBLE);
                    bottomParentRela.setVisibility(View.INVISIBLE);
                }
                break;



//                intent = new Intent(Login.this,MainInterface.class);
//                intent.putExtra("username",mEditText1.getText().toString());
//                startActivity(intent);

//                Log.d(TAG, "当前时间 本日开始时间: " + GetTime.getBeginTime("本日"));
//                Log.d(TAG, "当前时间 本日开始时间: " + GetTime.timeStampToDate(GetTime.getDayBegin()));
//                Log.d(TAG, "当前时间 本日开始时间: " + GetTime.timeStampToDate(GetTime.getDayBegin() - 1000*60*60*24));
//                Log.d(TAG, "当前时间 本日开始时间: " + GetTime.timeStampToDate(GetTime.getDayEnd()));
//                Log.d(TAG, "当前时间 本日开始时间戳: " + GetTime.getDayBegin());
//                Log.d(TAG, "当前时间 本日结束时间戳: " + GetTime.getDayEnd());
//                Log.d(TAG, "当前时间 本月开始时间戳: " + GetTime.monthTimeInMillis());
//                Log.d(TAG, "当前时间 本日开始时间戳: " + GetTime.timeStampToDate(GetTime.monthTimeInMillis()-1000));
//                Log.d(TAG, "当前时间 本月开始时间戳: " + GetTime.monthTimeInMillis_1());
//                Log.d(TAG, "当前时间 本日开始时间戳: " + GetTime.timeStampToDate(GetTime.monthTimeInMillis_1()));
            case R.id.forgetPassword:
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                View view = getLayoutInflater().inflate(R.layout.helper_forgetpassword,null);
                EditText userEdt = view.findViewById(R.id.username);
                EditText mailEdt = view.findViewById(R.id.mail);
                builder.setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String user = userEdt.getText().toString();
                                String mail = mailEdt.getText().toString();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String pwd = DaoUser.getPassword(user,mail);
                                        if("".equals(pwd)){
                                            makeToast("用户名或邮箱错误");
                                        }else {
                                            MailSend.sendMssage("北邮跑团密码找回","请妥善保管您的密码："+pwd,mail);
                                            makeToast("密码已发送至邮箱");
                                        }
                                    }
                                }).start();


                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();

                break;


        }

    }
    private void makeToast(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void isLoadBack(User user, String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    //写入账号密码
                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(mEditText1.getText().toString(), mEditText2.getText().toString());
                    editor.putString("registerNum", mEditText1.getText().toString());
                    editor.putString("password", mEditText2.getText().toString());
                    editor.apply();
                    editor.commit();
                    Cache.user = user;
                    userList.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Login.this,MainInterface.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getImgByRegisterNumBack(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               headImg.setImageDrawable(drawable);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //读取
        int fromActivity = mIntent == null ? 0 : mIntent.getIntExtra("fromActivity", 0);
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (fromActivity == ConstantUtil.CHANGE_PASSWORD) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("password", "");
            edit.putString(Cache.user.getRegisterNum(), "");
            edit.commit();
        }
        String registerNum = sp.getString("registerNum", null);
        String password = sp.getString("password", null);
        Map<String, String> map = (Map<String, String>) sp.getAll();
        keySet = map.keySet();
        if (registerNum != null && password != null) {
            mEditText1.setText(registerNum);
            mEditText2.setText(password);
        }
        userList.setAdapter(new LoginAdapter(keySet, this));

        String temp = mEditText1.getText().toString();
        if (temp != null && temp.length() == 10) mUserController.getImgByRegisterNum(temp);
        else headImg.setImageDrawable(null);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mIntent = intent;
    }
}

