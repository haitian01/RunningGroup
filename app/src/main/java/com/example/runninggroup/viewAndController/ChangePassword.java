package com.example.runninggroup.viewAndController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.util.ConstantUtil;


public class ChangePassword extends AppCompatActivity implements View.OnClickListener, UserController.UserControllerInterface {
    private Button mBtn_return,mBtn_resetData;
    private Button mBtn_changeData;
    private EditText mEd_oldPas;
    private EditText mEd_newPas;
    private EditText mEd_newPas_repeat;
    String username;
    private UserController mUserController = new UserController(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        initView();
        initEvent();
    }





    private void initView(){
        username = getIntent().getStringExtra("username");
        mEd_oldPas = findViewById(R.id.oldPassword);
        mEd_newPas = findViewById(R.id.newPassword);
        mEd_newPas_repeat = findViewById(R.id.repeatPassword);
        mBtn_return = findViewById(R.id.returnToMain);
        mBtn_resetData = findViewById(R.id.resetData);

    }

    private void initEvent(){
        mBtn_return.setOnClickListener(this);
        mBtn_resetData.setOnClickListener(this);

    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.returnToMain:
                final Intent intent = new Intent(ChangePassword.this, MainInterface.class);
                startActivity(intent);
                break;
            case R.id.resetData:
                String oldPwd = mEd_oldPas.getText().toString();
                String newPwd = mEd_newPas.getText().toString();
                String repeatPwd = mEd_newPas_repeat.getText().toString();
                if ("".equals(oldPwd) || "".equals(newPwd) || "".equals(repeatPwd))
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                else {
                    if (! newPwd.equals(repeatPwd))
                        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    else if (! oldPwd.equals(Cache.user.getPassword())){
                        Toast.makeText(this, "原密码不正确", Toast.LENGTH_SHORT).show();
                    }else {
                        mUserController.changePwd(newPwd);
                    }
                }
                break;



        }
    }
    private void makeToast(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChangePassword.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void changePwdBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res) {
                    Toast.makeText(ChangePassword.this, "修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePassword.this, Login.class);
                    intent.putExtra("fromActivity", ConstantUtil.CHANGE_PASSWORD);
                    startActivity(intent);
                }
                else Toast.makeText(ChangePassword.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
