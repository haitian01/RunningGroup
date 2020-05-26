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

public class WriteTask extends AppCompatActivity implements View.OnClickListener {
    EditText msg;
    Button releaseBtn;
    String username,group,num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writetask);
        initView();
        initEvent();
    }

    private void initView() {
        releaseBtn = findViewById(R.id.write_release);
        msg = findViewById(R.id.writetask_msg);
        username = getIntent().getStringExtra("username");
        group = getIntent().getStringExtra("group");
        num = getIntent().getStringExtra("name");
    }
    private void initEvent() {
        releaseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (R.id.writetask_release){
            case R.id.writetask_release:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if("SUCCESS".equals(DaoGroup.addTask(group,username,msg.getText().toString()))){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WriteTask.this,"发布成功！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WriteTask.this,"发布失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.writetask_return:
                Intent intent = new Intent(WriteTask.this,Manage.class);
                intent.putExtra("username",username);
                intent.putExtra("group",group);
                intent.putExtra("num",num);
                startActivity(intent);
        }
    }
}
