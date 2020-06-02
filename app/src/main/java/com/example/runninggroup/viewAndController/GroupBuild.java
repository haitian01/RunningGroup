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
import com.example.runninggroup.viewAndController.fragment.FragmentGroup;

public class GroupBuild extends AppCompatActivity implements View.OnClickListener {
    EditText groupNameEdt,sloganEdt;

    Button build;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuild);
        initView();
        initEvent();
    }

    private void initView() {
        build = findViewById(R.id.build);
        groupNameEdt = findViewById(R.id.groupname);
        sloganEdt = findViewById(R.id.slogan);
    }
    private void initEvent() {
        build.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.build:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        username = getIntent().getStringExtra("username");
                        if("SUCCESS".equals(DaoGroup.addGroup(groupNameEdt.getText().toString(),username,null,sloganEdt.getText().toString()))){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GroupBuild.this,"创建成功！",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(GroupBuild.this, MainInterface.class);
                                    intent.putExtra("id",3);
                                    intent.putExtra("username",username);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GroupBuild.this,"创建失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).start();

        }
    }
}
