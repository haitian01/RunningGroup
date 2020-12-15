package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.controller.TeamNoticeController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.TeamNotice;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.BitmapUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.view.MyDialog;
import com.example.runninggroup.viewAndController.adapter.WriteAdapter;

import java.util.ArrayList;

public class WriteNotice extends AppCompatActivity implements View.OnClickListener, TeamNoticeController.TeamNoticeControllerInterface, FileController.FileControllerInterface {
    RecyclerView imgRecy;
    Button releaseBtn;
    ImageView writeTurn;
    EditText msgEdt;
    String username;
    private static final int REQUEST_CODE = 1;
    private static final int MAX_SELECT_COUNT = 1;
    private ArrayList<String> paths = new ArrayList<>();
    private ArrayList<Bitmap> mBitmaps = new ArrayList<>();
    private WriteAdapter writeAdapter;
    private TeamNoticeController mTeamNoticeController = new TeamNoticeController(this);
    private FileController mFileController = new FileController(this);
    private ArrayList<String> selected = new ArrayList<>();
    private MyDialog mMyDialog;
    private Runtime runtime = Runtime.getRuntime();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < mBitmaps.size(); i++) {
            if (! mBitmaps.get(i).isRecycled()) mBitmaps.get(i).recycle();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                showContent(data);
                break;
        }

    }

    private void initView() {
        releaseBtn = findViewById(R.id.write_release);
        writeTurn = findViewById(R.id.write_return);
        msgEdt = findViewById(R.id.write_msg);
        imgRecy = findViewById(R.id.img_recy);
        mMyDialog = new MyDialog(this);
        writeAdapter = new WriteAdapter(mBitmaps);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        imgRecy.setAdapter(writeAdapter);
        imgRecy.setLayoutManager(gridLayoutManager);

    }

    private void initEvent() {
        writeTurn.setOnClickListener(this);
        releaseBtn.setOnClickListener(this);
        mMyDialog.setOnButtonClickListener(new MyDialog.OnButtonClickListener() {
            @Override
            public void camera() {

            }

            @Override
            public void gallery() {
                selectImg();
                mMyDialog.dismiss();
            }

            @Override
            public void cancel() {

            }
        });
        writeAdapter.setImgOnClickListener(new WriteAdapter.ImgOnClickListener() {
            @Override
            public void addImgOnClick() {
                mMyDialog.show();

            }

            @Override
            public void imgOnClick() {
                Toast.makeText(WriteNotice.this, "click", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write_return:
                WindowsEventUtil.systemBack();
                break;
            case R.id.write_release:
                String noticeMsg = msgEdt.getText().toString();
                if (StringUtil.isStringNull(noticeMsg))
                    Toast.makeText(this, "内容不可以为空", Toast.LENGTH_SHORT).show();
                else {

                    /**
                     * 添加一个通知类型的公告，由管理员发布
                     */

                    TeamNotice teamNotice = new TeamNotice();
                    Team team = new Team();
                    team.setId(Cache.user.getTeam().getId());
                    teamNotice.setTeam(team);
                    teamNotice.setNoticeMsg(noticeMsg);
                    teamNotice.setNoticeType(0);
                    teamNotice.setTopping(1);
                    User user = new User();
                    user.setId(Cache.user.getId());
                    teamNotice.setUser(user);
                    teamNotice.setImgNum(mBitmaps.size());
                    mTeamNoticeController.addTeamNotice(teamNotice);

                }
                break;

        }
    }

    private void showContent(Intent data) {
        if (data != null) {
            ArrayList<String> strings = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
//            paths.addAll(strings);
            for (int i = 0; i < strings.size(); i++) {
                mBitmaps.add(BitmapUtil.copyFile(strings.get(i)));
            }
            writeAdapter.notifyDataSetChanged();
        }




    }

    public void selectImg() {
        ImageSelector.builder()
                .useCamera(true) // 设置是否使用拍照
                .setSingle(false)  //设置是否单选
                .setMaxSelectCount(MAX_SELECT_COUNT - mBitmaps.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                .setSelected(selected) // 把已选的图片传入默认选中。
                .start(this, REQUEST_CODE); // 打开相册
    }

    @Override
    public void addTeamNoticeBack(String res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res ==  null || "0".equals(res)) Toast.makeText(WriteNotice.this, "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    mFileController.uploadMoreNotice(WriteNotice.this, mBitmaps, Integer.parseInt(res));
                }
            }
        });
    }

    @Override
    public void uploadMoreNoticeBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WriteNotice.this, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(WriteNotice.this, MainInterface.class);
                    startActivity(intent);
                }
            }
        });
    }
}
