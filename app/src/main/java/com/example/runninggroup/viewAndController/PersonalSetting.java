package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.util.WindowsEventUtil;

public class PersonalSetting extends AppCompatActivity{
    String username;
    ListView mListView;
    ImageView backImg;
    final int CHANGE_MESSAGE = 0;
    final int CHANGE_PWD = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        initView();
        initEvent();
    }










    private void initView() {
        mListView = findViewById(R.id.settingList);
        backImg = findViewById(R.id.back);
    }
    private void initEvent() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case CHANGE_MESSAGE:
                        Intent intent = new Intent(PersonalSetting.this, PersonalMessageChangeActivity.class);
                        startActivity(intent);
                        break;
                    case CHANGE_PWD:
                        Intent intent1 = new Intent(PersonalSetting.this,ChangePassword.class);
                        startActivity(intent1);
                        break;
                }
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
    }


}
