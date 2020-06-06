package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoAct;
import com.example.runninggroup.model.DaoDynamic;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.util.BitmapUtil;
import com.example.runninggroup.util.CharacterUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.Inflater;

public class CardPersonal extends AppCompatActivity {

    private Button mBtn_cardperson;
    private TimePicker startTimePicker,endTimePicker;
    private DatePicker mDatePicker;
    private EditText mEditText,beginEdt,endEdt;
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
    File mFile;
    Bitmap bitmap;
    private String img_src;
    private final int SELECT_PHOTO = 2;
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardpersonal);
        initView();
        initEvent();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                switch (resultCode) {
                    case RESULT_OK:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Uri uri = data.getData();
                                img_src = uri.getPath();//这是本机的图片路径

                                ContentResolver cr = getContentResolver();
                                try {
                                    InputStream inputStream = cr.openInputStream(uri);
                                    bitmap = BitmapFactory.decodeStream(inputStream);
                                    inputStream.close();

                                    String[] proj = {MediaStore.Images.Media.DATA};
                                    CursorLoader loader = new CursorLoader(CardPersonal.this, uri, proj, null, null, null);
                                    Cursor cursor = loader.loadInBackground();
                                    if (cursor != null) {
                                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                        cursor.moveToFirst();

                                        img_src = cursor.getString(column_index);//图片实际路径

                                    }
                                    cursor.close();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mImageView.setImageBitmap(bitmap);
                                        }
                                    });


                                } catch (FileNotFoundException e) {
                                    Log.e("Exception", e.getMessage(), e);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                        break;
                }
                break;


        }

    }

    private void initView() {
        username = getIntent().getStringExtra("username");
        //性别
        new Thread(new Runnable() {
            @Override
            public void run() {
                sex = DaoUser.getSex(username);
            }
        }).start();
        beginEdt = findViewById(R.id.begin_place);
        endEdt = findViewById(R.id.end_place);
        mImageView = findViewById(R.id.img);
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
                            //发表动态
                            try {
                                String path = BitmapUtil.saveMyBitmap(CardPersonal.this,bitmap,DaoUser.getCardImgName(username,beginTime));
                                mFile = new File(path);
                                ImgUpload.uploadFile(mFile,DaoUser.getCardImgName(username,beginTime));
                            }catch (Exception e){
                                makeToast("图片未发表");
                            }
                            if(DaoAct.insertAct(username,beginTime,endTime,length,score,type,beginEdt.getText().toString(),endEdt.getText().toString())){
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("ResourceType")
                                    @Override
                                    public void run() {
                                        mEditText.setText("");
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CardPersonal.this);
                                        final View view1=getLayoutInflater().inflate(R.layout.helper_cardok,null);
                                        TextView  mTextView = view1.findViewById(R.id.act_type);
                                        TextView  mTextView1 = view1.findViewById(R.id.act_time);
                                        TextView  mTextView2 = view1.findViewById(R.id.act_length);
                                        TextView  mTextView3 = view1.findViewById(R.id.act_score);
                                        mTextView.setText(type);
                                        mTextView1.setText(CharacterUtil.getTimeLength(beginTime,endTime));
                                        mTextView2.setText(length+"");
                                        mTextView3.setText(Double.toString(score));
                                        builder.setView(view1).setPositiveButton("返回主页", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(CardPersonal.this,MainInterface.class);
                                                intent.putExtra("id",0);
                                                intent.putExtra("username",username);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton("继续打卡", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).create().show();
                                    }
                                });


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

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CardPersonal.this);
                builder.setTitle("请选择头像：");
                builder.setMessage("可以通过相机或者相册选取头像。");
                builder.setPositiveButton("相册选取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, SELECT_PHOTO);
                    }
                });
                builder.create();
                builder.show();
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

