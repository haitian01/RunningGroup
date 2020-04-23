package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;

public class GroupMessage extends AppCompatActivity {
    TextView nameText,groupText,numText;

    Intent mIntent;
    String name;
    String group;
    String num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupmessage);
        initView();
        initEvent();
    }

    private void initView() {
        nameText = findViewById(R.id.name);
        groupText = findViewById(R.id.group);
        numText = findViewById(R.id.num);
        mIntent = getIntent();

        group = mIntent.getStringExtra("group");
        num = mIntent.getStringExtra("num");
        setView();
    }
    private void initEvent() {
    }
    private void setView(){

        groupText.setText(group);
        numText.setText(num);


    }
}
