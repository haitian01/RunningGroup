package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;

import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.DensityUtil;
import com.example.runninggroup.util.MailSend;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.view.KyLoadingBuilder;
import com.example.runninggroup.view.WaringDialog;
import com.example.runninggroup.viewAndController.adapter.LoginAdapter;

import java.util.HashSet;
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
    private KyLoadingBuilder kyLoadingBuilder;
    private WaringDialog mWaringDialog;
    private long startTime;
    private long endTime;
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


        mEditText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }



    private void initEvent() {
        registerTxt.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgetPwdTxt.setOnClickListener(this);
        downImg.setOnClickListener(this);
        /**
         * 监听软键盘事件，调正UI
         */
        parentRela.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                parentRela.getWindowVisibleDisplayFrame(r);
                int screenHeight = parentRela.getRootView()
                        .getHeight();
                int heightDifference = screenHeight - (r.bottom);
                if (heightDifference > 200) {
                    //监测软键盘显示
                    bottomToUp();

                } else {
                    //监测软键盘隐藏
                    upToBottom();

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
                changeLoginBtn();

            }
        });
        mEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable s) {
                changeLoginBtn();
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

    /**
     * 底部的忘记密码和注册抬到上面
     */
    private void bottomToUp () {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_TOP, loginBtn.getId());
        layoutParams.setMargins(0, 250, 0, 0);
        bottomParentRela.setLayoutParams(layoutParams);
    }

    /**
     * 上面的忘记密码和注册回到底部
     */
    public void upToBottom () {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.setMargins(0, 0, 0, 50);
        bottomParentRela.setLayoutParams(layoutParams);
    }

    /**
     * 根据editText动态改变btn颜色和是否可点击
     */
    public void changeLoginBtn () {
        String registerNum = mEditText1.getText().toString();
        String password = mEditText2.getText().toString();
        if (StringUtil.isStringNull(registerNum) || StringUtil.isStringNull(password)) {
            loginBtn.setClickable(false);
            loginBtn.setBackgroundResource(R.drawable.login_btn_style_unclick);
        }else {
            loginBtn.setClickable(true);
            loginBtn.setBackgroundResource(R.drawable.login_btn_style);
        }
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
                    startTime = System.currentTimeMillis();
                    WindowsEventUtil.hideSoftInput(Login.this, loginBtn);
                    upToBottom();
                    kyLoadingBuilder = new KyLoadingBuilder(this);
                    kyLoadingBuilder.setText("正在登录...");
                    //builder.setOutsideTouchable(false);//点击空白区域是否关闭
                    //builder.setBackTouchable(true);//按返回键是否关闭
                    kyLoadingBuilder.show();
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


            case R.id.forgetPassword:
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                View view = getLayoutInflater().inflate(R.layout.helper_forget_password,null);
                EditText userEdt = view.findViewById(R.id.username);
                EditText mailEdt = view.findViewById(R.id.mail);
                builder.setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



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


    @Override
    public void isLoadBack(User user, String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    endTime = System.currentTimeMillis();
                    if (endTime - startTime < ConstantUtil.MAX_KYLOADING_WAIT_TIME) Thread.sleep(ConstantUtil.MAX_KYLOADING_WAIT_TIME - (endTime - startTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if (user != null) {
                    kyLoadingBuilder.setText("登录成功");
                    try {
                        Thread.sleep(ConstantUtil.MAX_KYLOADING_SHOW_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                    mWaringDialog = new WaringDialog(Login.this, msg, "登录失败", "确定");
                    mWaringDialog.setOnButtonClickListener(new WaringDialog.OnButtonClickListener() {
                        @Override
                        public void define() {
                            mWaringDialog.dismiss();
                        }
                    });
                    mWaringDialog.show();
                }
                kyLoadingBuilder.dismiss();//关闭
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

        /**
         * user列表的大小
         */
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(Login.this, ((keySet.size() - 2) > 0 ? (keySet.size() - 2) : 0) * 60));
        layoutParams.addRule(RelativeLayout.ALIGN_TOP, mEditText2.getId());
        layoutParams.setMargins( DensityUtil.dip2px(Login.this,50), 0, DensityUtil.dip2px(Login.this,50), 0);
        userList.setLayoutParams(layoutParams);



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

