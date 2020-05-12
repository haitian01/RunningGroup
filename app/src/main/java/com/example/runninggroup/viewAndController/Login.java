package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.PostRequest;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText1,mEditText2;
    private Button mButton1,mButton2,mButton3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView() {
        mEditText1=findViewById(R.id.username);
        mEditText2=findViewById(R.id.password);
        mButton1=findViewById(R.id.register);
        mButton2=findViewById(R.id.login);
        mButton3=findViewById(R.id.forgetPassword);
        mEditText1.setText("tom");
        mEditText2.setText("123");

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
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        if("SUCCESS".equals(DaoUser.isLoad(mEditText1.getText().toString(),mEditText2.getText().toString()))){
//
//                            Intent intent = new Intent(Login.this,MainInterface.class);
//                            intent.putExtra("username",mEditText1.getText().toString());
//                            startActivity(intent);
//                        }else {
//                            Looper.prepare();
//                            Toast.makeText(Login.this,"登陆失败",Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                        }
//
//
//                    }
//                }).start();
                intent = new Intent(Login.this,MainInterface.class);
                intent.putExtra("username",mEditText1.getText().toString());
                startActivity(intent);


                break;
            case R.id.forgetPassword:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DaoUser.getMyGroup(mEditText1.getText().toString());
                    }
                }).start();
                break;


        }

    }
}

