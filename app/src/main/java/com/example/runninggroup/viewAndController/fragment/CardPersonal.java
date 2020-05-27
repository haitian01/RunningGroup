package com.example.runninggroup.viewAndController.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CardPersonal extends AppCompatActivity {

    private Button mBtn_cardperson;
    private TimePicker startTimePicker,endTimePicker;
    private DatePicker mDatePicker;
    private EditText mEditText;
    long beginTime;
    long endTime;
    int length;
    String username;
    String mBeginTime;
    String mEndTime;

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
        startTimePicker = findViewById(R.id.start_time);
        endTimePicker = findViewById(R.id.end_time);
        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);
        mDatePicker = findViewById(R.id.date);
        mEditText = findViewById(R.id.length);
    }
    private void initEvent() {
        mBtn_cardperson.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.card_personal:

                        mBeginTime = mDatePicker.getYear()+"-"+appendZero(mDatePicker.getMonth())+"-"+appendZero(mDatePicker.getDayOfMonth())+" "+appendZero(startTimePicker.getHour())+":"+appendZero(startTimePicker.getMinute());
                        mEndTime = mDatePicker.getYear()+"-"+appendZero(mDatePicker.getMonth())+"-"+appendZero(mDatePicker.getDayOfMonth())+" "+appendZero(endTimePicker.getHour())+":"+appendZero(endTimePicker.getMinute());

                        try {
                            beginTime = new SimpleDateFormat("yyyy-MM--dd HH:mm").parse(mBeginTime).getTime();
                            endTime = new SimpleDateFormat("yyyy-MM--dd HH:mm").parse(mEndTime).getTime();
                            Toast.makeText(CardPersonal.this,mBeginTime+"\n"+mEndTime,Toast.LENGTH_LONG).show();

                        } catch (ParseException e) {
                            Toast.makeText(CardPersonal.this,"时间转换错误",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        try {
                            length = Integer.parseInt(mEditText.getText().toString());

                        } catch (NumberFormatException e) {
                            Toast.makeText(CardPersonal.this,"跑步长度格式错误",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        break;
                }
            }
        });




    }

    private String appendZero(int obj){
        if (obj < 10) {
            return "0" + obj;
        } else {
            return obj+"";
        }

    };


    }

