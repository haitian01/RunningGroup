package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.viewAndController.adapter.DynamicAdapter;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;

import java.util.ArrayList;
import java.util.List;

public class FriendMessage extends AppCompatActivity {
    TextView nameText,groupText,lengthText;
    ListView dynamicListView;
    List<DynamicHelper> mList;
    Intent mIntent;
    String name;
    String group;
    String length;
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
        lengthText = findViewById(R.id.runNum);
        dynamicListView = findViewById(R.id.dynamic);
        mIntent = getIntent();
        name = mIntent.getStringExtra("name");
        group = mIntent.getStringExtra("group");
        length = mIntent.getStringExtra("length");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mList = DaoFriend.getDynamic(name);
                for(DynamicHelper list:mList){
                    list.setDynamic_img("defaultpic");
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dynamicListView.setAdapter(new DynamicAdapter(getLayoutInflater(),mList));
        setView();
    }
    private void initEvent() {
        Toast.makeText(FriendMessage.this,name+"",Toast.LENGTH_SHORT).show();

    }
    private void setView(){
        nameText.setText(name);
        groupText.setText(group);
        lengthText.setText(length);

    }
}
