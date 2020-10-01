package com.example.runninggroup.viewAndController;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.runninggroup.R;
import com.example.runninggroup.cache.UserCache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.PostRequest;

import com.example.runninggroup.util.MailSend;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.viewAndController.TimeAndData.GetTime;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Login extends AppCompatActivity implements View.OnClickListener, UserController.UserControllerInterface {
    public static final String TAG = "MyActivityTest";
    private EditText mEditText1,mEditText2;
    private Button mButton1,mButton2,mButton3;
    private String registerNum;
    private String password;
    private UserController mUserController = new UserController(this);
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
        //读取
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        registerNum = sp.getString("registerNum", null);
        password = sp.getString("password",null);



        //
        mEditText1=findViewById(R.id.registerNum);
        mEditText2=findViewById(R.id.password);
        mButton1=findViewById(R.id.register);
        mButton2=findViewById(R.id.login);
        mButton3=findViewById(R.id.forgetPassword);
        if(registerNum != null){
            mEditText1.setText(registerNum);
        }
        if(password != null){
            mEditText2.setText(password);
        }
//        if (registerNum != null && password != null) {
//            mUserController.isLoad(registerNum, password);
//        }
        mEditText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }


    private void initEvent() {
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
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
    public void isLoadBack(User user) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    //写入账号密码
                    SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("registerNum",mEditText1.getText().toString());
                    editor.putString("password", mEditText2.getText().toString());
                    editor.apply();
                    UserCache.user = user;
                    Intent intent = new Intent(Login.this,MainInterface.class);
                    startActivity(intent);


                    Toast.makeText(Login.this, user.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

