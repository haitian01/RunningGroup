package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText1,mEditText2;
    private Button mButton1,mButton2;
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

    }

    private void initEvent() {
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                Intent intent = new Intent(this,Register.class);
                startActivity(intent);
                break;
            case R.id.login:
                intent = new Intent(this,MainInterface.class);
                startActivity(intent);
                break;


        }

    }
}
