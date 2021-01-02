package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.view.KyLoadingBuilder;
import com.example.runninggroup.viewAndController.adapter.FriendMessageAdapter;

import java.util.List;

public class AddFriendActivity extends AppCompatActivity implements UserController.UserControllerInterface, View.OnClickListener, FriendApplicationController.FriendApplicationControllerInterface {
    private ImageView mImageView;
    private TextView usernameText,otherText,sendText,cancleText;
    private EditText msgEdt;
    private UserController mUserController = new UserController(this);
    private FriendApplicationController mFriendApplicationController = new FriendApplicationController(this);
    private KyLoadingBuilder mKyLoadingBuilder;
    private long startTime;
    private long endTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
        initEvent();
    }

    private void initView() {

        mImageView = findViewById(R.id.pic);
        usernameText = findViewById(R.id.username);
        otherText = findViewById(R.id.other);
        sendText = findViewById(R.id.send);
        cancleText = findViewById(R.id.cancel);
        msgEdt = findViewById(R.id.msg);
        mUserController.getHeadImg(ImgNameUtil.getUserHeadImgName(Cache.friend.getId()));
        usernameText.setText(Cache.friend.getUsername());
        otherText.setText(Cache.friend.getSex() == 1 ? "男" : "女");




    }
    private void initEvent() {
        cancleText.setOnClickListener(this);
        sendText.setOnClickListener(this);



    }
    private void setView(){



    }

    @Override
    public void getHeadImgBack(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null)
                mImageView.setImageDrawable(drawable);
                else {
                    if (Cache.friend != null) mImageView.setImageResource(Cache.friend.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                mKyLoadingBuilder = new KyLoadingBuilder(this);
                mKyLoadingBuilder.setText("正在发送中...");
                mKyLoadingBuilder.show();
                startTime = System.currentTimeMillis();
                mFriendApplicationController.sendFriendApplication(StringUtil.isStringNull(msgEdt.getText().toString()) ? "请求添加好友" : msgEdt.getText().toString());
                break;

            case R.id.cancel:
                WindowsEventUtil.systemBack();

            case R.id.setting:
                break;
        }
    }

    @Override
    public void sendFriendApplicationBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    endTime = System.currentTimeMillis();
                    if (endTime - startTime < ConstantUtil.MAX_KYLOADING_WAIT_TIME) Thread.sleep(ConstantUtil.MAX_KYLOADING_WAIT_TIME - (endTime - startTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mKyLoadingBuilder.setText(res ? "发送成功" : "发送失败");
                try {
                    Thread.sleep(ConstantUtil.MAX_KYLOADING_SHOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mKyLoadingBuilder.dismiss();
                if (res) {
                    Intent intent = new Intent(AddFriendActivity.this, FriendMessage.class);
                    startActivity(intent);
                }

            }
        });
    }
}
