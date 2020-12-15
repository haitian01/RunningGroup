package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.ActController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.Act;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.StringUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CardPersonal extends AppCompatActivity implements ActController.ActControllerInterface, UserController.UserControllerInterface {

    private Button mBtn_cardperson;
    private TimePicker startTimePicker,endTimePicker;
    private DatePicker mDatePicker;
    private EditText lengthEdt,placeEdt;
    Timestamp beginTime;
    Timestamp endTime;
    Double length;
    String place;
    String mBeginTime;
    String mEndTime;
    private ActController mActController = new ActController(this);
    private UserController mUserController = new UserController(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_personal);
        initView();
        initEvent();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {
        placeEdt = findViewById(R.id.place);
        mBtn_cardperson = findViewById(R.id.card_personal);
        startTimePicker = findViewById(R.id.start_time);
        endTimePicker = findViewById(R.id.end_time);
        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);
        mDatePicker = findViewById(R.id.date);
        lengthEdt = findViewById(R.id.length);

    }
    private void initEvent() {

        mBtn_cardperson.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (StringUtil.isStringNull(placeEdt.getText().toString()) || StringUtil.isStringNull(lengthEdt.getText().toString())) {
                    Toast.makeText(CardPersonal.this, "内容不完整", Toast.LENGTH_LONG).show();
                }else {


                    if (! isAllowed(lengthEdt.getText().toString())) {
                        Toast.makeText(CardPersonal.this, "跑步长度格式错误", Toast.LENGTH_LONG).show();
                    }else {
                        //开始和结束时间（字符串）
                        mBeginTime = mDatePicker.getYear() + "-" + appendZero(mDatePicker.getMonth()+1) + "-" + appendZero(mDatePicker.getDayOfMonth()) + " " + appendZero(startTimePicker.getHour()) + ":" + appendZero(startTimePicker.getMinute());
                        mEndTime = mDatePicker.getYear() + "-" + appendZero(mDatePicker.getMonth()+1) + "-" + appendZero(mDatePicker.getDayOfMonth()) + " " + appendZero(endTimePicker.getHour()) + ":" + appendZero(endTimePicker.getMinute());

                        //地点
                        place = placeEdt.getText().toString();
                        //开始和结束时间（时间戳）
                        long begin = 0;
                        long end = 0;

                        try {
                            begin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mBeginTime).getTime();
                            end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mEndTime).getTime();
                            beginTime = new Timestamp(begin);
                            endTime = new Timestamp(end);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        length = Double.valueOf(lengthEdt.getText().toString());

                        //插入活动记录
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CardPersonal.this);
//                    final View view1=getLayoutInflater().inflate(R.layout.helper_cardok,null);
//                    TextView  mTextView = view1.findViewById(R.id.act_type);
//                    TextView  mTextView1 = view1.findViewById(R.id.act_time);
//                    TextView  mTextView2 = view1.findViewById(R.id.act_length);
//                    TextView  mTextView3 = view1.findViewById(R.id.act_score);
//                    mTextView.setText(type);
//                    mTextView1.setText(CharacterUtil.getTimeLength(beginTime,endTime));
//                    mTextView2.setText(length+"");
//                    mTextView3.setText(Double.toString(score));
//                    builder.setView(view1).setPositiveButton("返回主页", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(CardPersonal.this,MainInterface.class);
//                            intent.putExtra("id",0);
//                            intent.putExtra("username",username);
//                            startActivity(intent);
//                        }
//                    }).setNegativeButton("继续打卡", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    }).create().show();

                        if (begin >= end) {
                            Toast.makeText(CardPersonal.this, "时间输入错误", Toast.LENGTH_SHORT).show();
                        }else {
                            /**
                             * 发送网络请求，打卡
                             */
                            Act act = new Act();
                            act.setBeginTime(beginTime);
                            act.setEndTime(endTime);
                            act.setPlace(place);
                            act.setRunLen(length);
                            User user = new User();
                            user.setId(Cache.user.getId());
                            act.setUser(user);
                            mActController.addAct(act);
                        }




                    }

                }

            }


        });


    }

    /**
     * 添加活动回调
     * @param res
     */

    @Override
    public void addActBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CardPersonal.this, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    //重新加载一下user
                    User user = new User();
                    user.setId(Cache.user.getId());
                    mUserController.selectUserByUser(user);

                    Intent intent = new Intent(CardPersonal.this, MainInterface.class);
                    startActivity(intent);
                }
            }
        });
    }


    /**
     * 判断一个String数是否为合法的double
     * @param doubleString
     * @return
     */
    private static boolean isAllowed (String doubleString) {
        if (doubleString.startsWith(".") || doubleString.endsWith(".")) return false;
        else {
            int count = 0;
            for (int i = 0; i < doubleString.length(); i++) {
                char c = doubleString.charAt(i);
                if (c == '.') count++;
            }
            if (count > 1) return false;
            else {
                String[] split = doubleString.split("\\.");
                String left = split[0];
                return Integer.valueOf(left).toString().equals(left);
            }
        }

    }

    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(CardPersonal.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() > 0) {
                    Cache.user = users.get(0);
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
    }



    }

