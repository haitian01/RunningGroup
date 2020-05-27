package com.example.runninggroup.viewAndController.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;

public class CardPersonal extends AppCompatActivity {

    private Button mBtn_cardperson;
    private EditText mEt_date, mEt_position, mEt_length;
    private RadioGroup rg;
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardpersonal);
        initView();
        initEvent();
    }


    private void initView() {
        username = getIntent().getStringExtra("username");
        mBtn_cardperson = findViewById(R.id.card_personal);
        mEt_date = findViewById(R.id.date);
        mEt_position = findViewById(R.id.position);
        mEt_length = findViewById(R.id.length);
        rg = findViewById(R.id.radio_group);
    }
    private void initEvent() {
        mBtn_cardperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.card_personal:
                        Toast.makeText(CardPersonal.this,"打卡完成",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.single:
                        Log.e("rb","选择个人打卡");
                        break;
                    case R.id.group:
                        Log.e("rb","选择跑团打卡");
                        break;

                }
            }
        });

    }


    }

