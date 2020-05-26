package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;

public class Manage extends AppCompatActivity implements View.OnClickListener {
    Button releaseBtn;
    EditText msg;
    Intent mIntent;
    String username;
    String group;
    String num;
    String leader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        initView();
        initEvent();
    }

    private void initView() {
        releaseBtn = findViewById(R.id.release);
        msg = findViewById(R.id.release_msg);
        mIntent = getIntent();
        username = mIntent.getStringExtra("username");
        group = mIntent.getStringExtra("group");
        num = mIntent.getStringExtra("num");
    }
    private void initEvent() {
       releaseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.release:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if("SUCCESS".equals(DaoGroup.addTask(group,username,msg.getText().toString()))){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Manage.this,"发布成功！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Manage.this,"发布失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                break;

        }

    }

}
