package com.example.runninggroup.viewAndController;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoAct;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CardPersonal extends AppCompatActivity {

    private Button mBtn_cardperson;
    private TimePicker startTimePicker,endTimePicker;
    private DatePicker mDatePicker;
    private EditText mEditText;
    private Spinner mSpinner;
    private String[] act_type={"常规跑步","全马/半马","校比赛"};
    private int score = 0;
    long beginTime;
    long endTime;
    int length = 0;
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
        mSpinner = findViewById(R.id.act_type);
        mSpinner.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,act_type));

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
                            beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mBeginTime).getTime();
                            endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mEndTime).getTime();
                            Toast.makeText(CardPersonal.this,beginTime+"\n"+endTime,Toast.LENGTH_LONG).show();

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
                        //插入活动记录
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(DaoAct.insertAct(username,beginTime,endTime,length,score)){
                                makeToast("打卡成功");

                            }else {
                                makeToast("打卡失败");
                            }
                        }
                    }).start();

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
    private void makeToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CardPersonal.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    }

