package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;

public class FriendMessage extends AppCompatActivity {
    TextView nameText,groupText;

    Intent mIntent;
    String name;
    String group;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendmessage);
        initView();
        initEvent();
    }

    private void initView() {
        nameText = findViewById(R.id.name);
        groupText = findViewById(R.id.group);
        mIntent = getIntent();
        name = mIntent.getStringExtra("name");
        group = mIntent.getStringExtra("group");
        setView();
    }
    private void initEvent() {
        Toast.makeText(FriendMessage.this,name+"",Toast.LENGTH_SHORT).show();

    }
    private void setView(){
        nameText.setText(name);
        groupText.setText(group);


    }
}
