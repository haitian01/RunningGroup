package com.example.runninggroup.viewAndController;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.WindowsEventUtil;

import java.text.SimpleDateFormat;

public class TeamNoticePage extends AppCompatActivity implements FileController.FileControllerInterface {
    private ImageView headImg, msgImg, backImg;
    private TextView nameTxt, dataTxt, msgTxt;
    private FileController mFileController = new FileController(this);
    private static final int HEAD_IMG = 0;
    private static final int MSG_IMG = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_notice_page);
        initView();
        initEvent();
    }

    private void initView() {
        headImg = findViewById(R.id.head_img);
        msgImg = findViewById(R.id.msg_img);
        backImg = findViewById(R.id.back);
        nameTxt = findViewById(R.id.name);
        dataTxt = findViewById(R.id.data);
        msgTxt = findViewById(R.id.msg);


        if (Cache.teamNotice != null && Cache.teamNotice.getUser() != null) {

            /**
             * 加载图片
             */
            mFileController.getImg(ImgNameUtil.getUserHeadImgName(Cache.teamNotice.getUser().getId()), HEAD_IMG);
            mFileController.getImg(ImgNameUtil.getNoticeImgName(Cache.teamNotice.getId(), 0),MSG_IMG);
            nameTxt.setText(Cache.teamNotice.getUser().getUsername());
            dataTxt.setText(new SimpleDateFormat("yy年MM月dd日 HH：mm").format(Cache.teamNotice.getUpdateTime()));
            msgTxt.setText(Cache.teamNotice.getNoticeMsg());
        }

    }


    private void initEvent() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
    }

    @Override
    public void getImgBack(Drawable drawable, int mark) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) {
                    if (mark == HEAD_IMG) headImg.setImageDrawable(drawable);
                    else if (mark == MSG_IMG) msgImg.setImageDrawable(drawable);
                }
            }
        });
    }
}
