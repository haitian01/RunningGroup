package com.example.runninggroup.viewAndController;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.runninggroup.model.DaoUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CardPersonal extends AppCompatActivity {

    private Button mBtn_cardperson;
    private TimePicker startTimePicker,endTimePicker;
    private DatePicker mDatePicker;
    private EditText mEditText;
    private Spinner mSpinner;
    private String[] act_type={"常规跑步","全马/半马","校比赛"};
    private double score = 0;
    private String type;
    long beginTime;
    long endTime;
    int length = 0;
    String username;
    String mBeginTime;
    String mEndTime;
    String sex;

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
        //活动类型选择
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = act_type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = act_type[0];
            }
        });

        mBtn_cardperson.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //开始和结束时间（字符串）
                mBeginTime = mDatePicker.getYear() + "-" + appendZero(mDatePicker.getMonth()+1) + "-" + appendZero(mDatePicker.getDayOfMonth()) + " " + appendZero(startTimePicker.getHour()) + ":" + appendZero(startTimePicker.getMinute());
                mEndTime = mDatePicker.getYear() + "-" + appendZero(mDatePicker.getMonth()+1) + "-" + appendZero(mDatePicker.getDayOfMonth()) + " " + appendZero(endTimePicker.getHour()) + ":" + appendZero(endTimePicker.getMinute());
                //性别
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sex = DaoUser.getSex(username);
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //开始和结束时间（时间戳）
                try {
                    beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mBeginTime).getTime();
                    endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mEndTime).getTime();
//                    Toast.makeText(CardPersonal.this, beginTime + "\n" + endTime, Toast.LENGTH_LONG).show();

                } catch (ParseException e) {
//                    Toast.makeText(CardPersonal.this, "时间转换错误", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                try {
                    length = Integer.parseInt(mEditText.getText().toString());
                    //分数
                    switch (type) {
                        case "常规跑步":
                            score = length / 1500.00;
                            break;
                        case "全马/半马":
                            score = length/1000.00;
                            break;
                        case "校比赛":
                            score = length/1000.00;
                            break;
                    }
                    score = ("男".equals(sex)) ? score : 1.5*score;
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
                } catch (NumberFormatException e) {
                    Toast.makeText(CardPersonal.this, "跑步长度格式错误", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
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

