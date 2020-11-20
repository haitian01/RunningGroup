package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.MailSend;
import com.example.runninggroup.util.PermisionUtil;

import java.util.Random;
import java.util.UUID;

public class Register extends AppCompatActivity implements View.OnClickListener, UserController.UserControllerInterface {
    private EditText mEditText1,mEditText2,mEditText3,registerNumText,testText;
    private Button mButton1,mButton2,sendBtn;
    private RadioGroup mRadioGroup;
    private String sex = "女";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private String testNum;
    private String mail;
    private UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        PermisionUtil.verifyStoragePermissions(this);
        initView();
        initEvent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_EXTERNAL_STORAGE :
                for (int grantResult : grantResults) {
                    if(grantResult == -1){
                        System.exit(0);
                    }
                }
                break;
        }
    }

    private void initView() {
        mEditText1=findViewById(R.id.username);
        mEditText2=findViewById(R.id.password);
        mEditText3 = findViewById(R.id.repeatPassword);
        registerNumText = findViewById(R.id.registerNum);
        testText = findViewById(R.id.test);
        mButton1=findViewById(R.id.register);
        sendBtn = findViewById(R.id.send);
        mRadioGroup = findViewById(R.id.rg);

        mEditText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEditText3.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    private void initEvent() {
        mButton1.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.male){
                    Toast.makeText(Register.this,"男",Toast.LENGTH_SHORT).show();
                    sex = "男";

                }else {
                    Toast.makeText(Register.this,"女",Toast.LENGTH_SHORT).show();
                    sex = "女";
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                if(testText.getText().toString().equals(testNum) && registerNumText.getText().toString().equals(mail)){
                    if(mEditText1.getText().toString().equals("") || mEditText2.getText().toString().equals("") || mEditText3.getText().toString().equals("")){
                        Toast.makeText(Register.this,"输入不完整",Toast.LENGTH_SHORT).show();
                    }else {
                        if(! mEditText2.getText().toString().equals(mEditText3.getText().toString())){
                            Toast.makeText(Register.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                        }else {
                            mUserController.register(mail ,mEditText2.getText().toString(), sex, mEditText1.getText().toString());
                        }
                    }
                }else {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                sendBtn.setClickable(false);
                            }
                        });
                        testNum = getRandom();
                        mail = registerNumText.getText().toString();
                        MailSend.sendMssage("北邮跑团账号注册","欢迎您的加入："+testNum,registerNumText.getText().toString());

                        for(int i=60;i>0;i--){
                            final int j = i;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    sendBtn.setText(j+"");
                                }
                            });

                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                sendBtn.setClickable(true);
                            }
                        });

                    }

                }).start();


                break;


        }

    }
    private String getRandom(){
        Random r = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<6;i++){
            stringBuffer.append(r.nextInt(9));
        }
        return stringBuffer.toString();
    }


    @Override
    public void registerBack(boolean res, String registerNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res) {
                    Toast.makeText(Register.this, "注册成功,账号为：" + registerNum, Toast.LENGTH_LONG).show();
                    SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("registerNum", registerNum);
                    edit.putString("password", mEditText2.getText().toString());
                    edit.commit();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
                else Toast.makeText(Register.this, registerNum, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
