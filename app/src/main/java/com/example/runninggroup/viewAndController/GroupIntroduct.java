package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoUser;

public class GroupIntroduct extends AppCompatActivity {
    TextView groupText,numText,leaderText;
    Button mButton;
    Intent mIntent;
    String group;
    String num;
    String leaderName;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupintroduct);
        initView();
        initEvent();
        setView();
    }

    private void initView() {
        groupText = findViewById(R.id.group);
        numText = findViewById(R.id.num);
        leaderText = findViewById(R.id.leaderName);
        mButton = findViewById(R.id.join);
    }
    private void initEvent() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButton.getText().toString().equals("加入")){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DaoUser.getMyGroup(username);

                            if("".equals(result)){
                                if(DaoUser.setMyGroup(username,group,1,"JOIN").equals("SUCCESS")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mButton.setText("退出");
                                            int number = Integer.parseInt(numText.getText().toString())+1;
                                            numText.setText(number+"");
                                        }
                                    });
                                    Looper.prepare();
                                    Toast.makeText(GroupIntroduct.this,"成功！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();

                                }else {
                                    Looper.prepare();
                                    Toast.makeText(GroupIntroduct.this,"失败！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }else {
                                Looper.prepare();
                                Toast.makeText(GroupIntroduct.this,"失败！\n您已经加入过跑团",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();

                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(DaoUser.setMyGroup(username,group,-1,"OUT").equals("SUCCESS")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mButton.setText("加入");
                                        int number = Integer.parseInt(numText.getText().toString())-1;
                                        numText.setText(number+"");
                                    }
                                });
                                Looper.prepare();
                                Toast.makeText(GroupIntroduct.this,"成功",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }else {
                                Looper.prepare();
                                Toast.makeText(GroupIntroduct.this,"失败",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }
            }
        });
    }
    private void setView() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                mIntent = getIntent();
                group = mIntent.getStringExtra("group");
                num = mIntent.getStringExtra("num");
                leaderName = mIntent.getStringExtra("leader");
                username = mIntent.getStringExtra("username");
                groupText.setText(group);
                numText.setText(num);
                leaderText.setText(leaderName);
                if(group.equals(DaoUser.getMyGroup(username))){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mButton.setText("退出");
                        }
                    });

                }
            }
        }).start();
    }
}
