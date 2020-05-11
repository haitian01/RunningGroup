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

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText1,mEditText2,mEditText3;
    private Button mButton1,mButton2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }
    private void initView() {
        mEditText1=findViewById(R.id.username);
        mEditText2=findViewById(R.id.password);
        mEditText3 = findViewById(R.id.repeatPassword);
        mButton1=findViewById(R.id.register);

    }
    private void initEvent() {
        mButton1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                if(mEditText1.getText().toString().equals("") || mEditText2.getText().toString().equals("") || mEditText3.getText().toString().equals("")){
                    Toast.makeText(Register.this,"输入不完整",Toast.LENGTH_SHORT).show();
                }else {
                    if(! mEditText2.getText().toString().equals(mEditText3.getText().toString())){
                        Toast.makeText(Register.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    }else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if("SUCCESS".equals(DaoUser.Register(mEditText1.getText().toString(),mEditText2.getText().toString()))){
                                    Intent intent = new Intent(Register.this,Login.class);
                                    startActivity(intent);
                                    Looper.prepare();
                                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    Looper.loop();

                                }else {
                                    Looper.prepare();
                                    Toast.makeText(Register.this,"注册失败",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }


                            }
                        }).start();
                    }
                }


        }

    }
}
