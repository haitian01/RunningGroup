package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoDynamic;

public class Write extends AppCompatActivity implements View.OnClickListener {
    Button releaseBtn;
    TextView returnText;
    EditText msgEdt;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();
        initEvent();
    }

    private void initView() {
        username = getIntent().getStringExtra("username");
        releaseBtn = findViewById(R.id.write_release);
        returnText = findViewById(R.id.write_return);
        msgEdt = findViewById(R.id.write_msg);
    }

    private void initEvent() {
        returnText.setOnClickListener(this);
        releaseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write_return:
                Intent intent = new Intent(Write.this,MainInterface.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.write_release:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //发表动态
                        String result = DaoDynamic.writeDynamic(username,msgEdt.getText().toString());
                        if("SUCCESS".equals(result)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Write.this,"发表成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Write.this,"发表失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
        }
    }
}
