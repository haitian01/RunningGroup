package com.example.runninggroup.viewAndController;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoFriend;

public class FriendManage extends AppCompatActivity {
    TextView nameText,groupText,lengthText;
    ListView friendManageList;
    String username;
    String name;
    String group;
    String length;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendmanage);
        initView();
        initEvent();
    }


    private void initView() {
        username = getIntent().getStringExtra("username");
        name = getIntent().getStringExtra("name");
        group = getIntent().getStringExtra("group");
        length = getIntent().getStringExtra("length");
        nameText = findViewById(R.id.friendmanage_name);
        groupText = findViewById(R.id.friendmanage_group);
        lengthText = findViewById(R.id.friendmanage_runNum);
        friendManageList = findViewById(R.id.friendmanage_list);
        nameText.setText(name);
        groupText.setText(group);
        lengthText.setText(length);
    }

    private void initEvent() {
        friendManageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //删除好友
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(DaoFriend.deleteFriend(username,name)){
                                    Toast.makeText(FriendManage.this, "删除成功", Toast.LENGTH_SHORT).show();
                                }else {Toast.makeText(FriendManage.this, "删除失败", Toast.LENGTH_SHORT).show();}
                            }
                        }).start();
                        break;
                }
            }
        });
    }
}
