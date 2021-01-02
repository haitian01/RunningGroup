package com.example.runninggroup.viewAndController;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.controller.TeamApplicationController;
import com.example.runninggroup.controller.TeamNoticeController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.TeamNotice;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.DensityUtil;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.view.MyDialog;
import com.example.runninggroup.view.WaringDialog;
import com.example.runninggroup.view.WaringDialogWithTwoButton;

import java.io.File;
import java.util.List;

public class TeamIntroduction extends AppCompatActivity implements FileController.FileControllerInterface, UserController.UserControllerInterface, TeamNoticeController.TeamNoticeControllerInterface {
    TextView teamNameTxt, teamRegisterNumTxt, buildTimeTxt, leaderTxt, campusTxt, collegeTxt, sizeTxt, lengthTxt, scoreTxt, noticeMsgTxt, noticeTxt;
    Button addBtn,deleteBtn;
    RelativeLayout teamManage,teamNotice,teamSort, members, teamMessage;
    LinearLayout teamFunction;
    ImageView teamImg, back, backImg;
    private final int SELECT_PHOTO = 2;
    private final int REQUESTCODE_CUTTING = 3;
    File file;
    Uri mImageUri;
    private List<TeamNotice> mTeamNoticeList;
    private FileController mFileController = new FileController(this);
    private UserController mUserController = new UserController(this);
    private TeamNoticeController mTeamNoticeController = new TeamNoticeController(this);
    private MyDialog mMyDialog;
    private static final int HEAD_IMG = 0;
    private static final int BACK_IMG = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_introduction);
        initView();
        initEvent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case SELECT_PHOTO:
                    if (data != null) {
                        //根据返回的data获取图片的uri
                        Uri uri = data.getData();
                        String sdCardDir = getExternalCacheDir().toString();
                        File appDir = new File(sdCardDir, "/GuanGuan/");
                        if (!appDir.exists()) appDir.mkdir();
                        file = new File(appDir, ImgNameUtil.getTeamBackImgName(Cache.team.getId()) + ".jpg");
                        //新建裁剪图片存放的uri
                        mImageUri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    }

                    break;
                case REQUESTCODE_CUTTING:
                    mFileController.upload(file, ImgNameUtil.getTeamBackImgName(Cache.team.getId()));
                    break;

            }
    }
    //裁剪图片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，这里可以将宽高作为参数传递进来
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        // 其实加上下面这两句就可以实现基本功能，
        //但是这样做我们会直接得到图片的数据，以bitmap的形式返回，在Intent中。
        // 而Intent传递数据大小有限制，1kb=1024字节，这样就对最后的图片的像素有限制。
        //intent.putExtra("return-data", true);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);

        // 解决不能传图片，Intent传递数据大小有限制，1kb=1024字节
        // 方法：裁剪后的数据不以bitmap的形式返回，而是放到磁盘中，更方便上传和本地缓存
        // 设置裁剪后的数据不以bitmap的形式返回，剪切后图片的位置，图片是否压缩等
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        // 调用系统的图片剪切
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    private void initEvent() {

        teamNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTeamNoticeList == null || mTeamNoticeList.size() == 0) {
                    WaringDialog waringDialog = new WaringDialog(TeamIntroduction.this, "本跑团暂无公告", "提示", "我知道了");
                    waringDialog.setOnButtonClickListener(new WaringDialog.OnButtonClickListener() {
                        @Override
                        public void define() {
                            waringDialog.dismiss();
                        }
                    });
                    waringDialog.show();
                }else {
                    Intent intent = new Intent(TeamIntroduction.this, TeamNoticeActivity.class);
                    startActivity(intent);
                }
            }
        });
        teamManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setId(Cache.user.getId());
                mUserController.selectUserByUser(user);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cache.user.getTeam() != null)
                    Toast.makeText(TeamIntroduction.this, "已加入过跑团！", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(TeamIntroduction.this, AddTeamActivity.class);
                    startActivity(intent);
                }
            }
        });
        teamSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamIntroduction.this, MemberSort.class);
                startActivity(intent);
            }
        });
        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamIntroduction.this, MemberManage.class);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaringDialogWithTwoButton waringDialogWithTwoButton = new WaringDialogWithTwoButton(TeamIntroduction.this, "退出跑团将会清空跑团内部累计里程和分数，您确定退出吗？", "取消","确定");
                waringDialogWithTwoButton.setOnButtonClickListener(new WaringDialogWithTwoButton.OnButtonClickListener() {
                    @Override
                    public void right() {
                        waringDialogWithTwoButton.dismiss();
                        mUserController.signOutTeam();
                    }

                    @Override
                    public void left() {
                        waringDialogWithTwoButton.dismiss();
                    }
                });
                waringDialogWithTwoButton.show();
            }
        });
        teamMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamIntroduction.this, TeamMessageChangeActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               WindowsEventUtil.systemBack();
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有管理权限
                if (Cache.user.getTeam() != null && Cache.user.getTeam().getId() == Cache.team.getId() && Cache.user.getTeamLevel() >= 2) {
                    mMyDialog.show();
                }
            }
        });

    }

    private void initView() {
        StatusBarUtils.setStatusBarFullTransparent(this);
        teamNameTxt = findViewById(R.id.teamName);
        teamRegisterNumTxt = findViewById(R.id.teamRegisterNum);
        buildTimeTxt = findViewById(R.id.buildTime);
        addBtn = findViewById(R.id.add);
        teamManage = findViewById(R.id.teamManage);
        teamMessage = findViewById(R.id.team);
        teamNotice = findViewById(R.id.teamNotice);
        teamSort = findViewById(R.id.teamSort);
        teamFunction = findViewById(R.id.teamFunction);
        teamImg = findViewById(R.id.teamImg);
        deleteBtn = findViewById(R.id.delete);
        members = findViewById(R.id.members);
        leaderTxt = findViewById(R.id.leader);
        campusTxt = findViewById(R.id.campus);
        collegeTxt = findViewById(R.id.college);
        sizeTxt = findViewById(R.id.size);
        lengthTxt = findViewById(R.id.length);
        scoreTxt = findViewById(R.id.score);
        back = findViewById(R.id.back);
        backImg = findViewById(R.id.backImg);
        noticeMsgTxt = findViewById(R.id.notice_msg);
        noticeTxt = findViewById(R.id.notice);
        mMyDialog = new MyDialog(this);
        mMyDialog.setOnButtonClickListener(new MyDialog.OnButtonClickListener() {
            @Override
            public void camera() {
                mMyDialog.dismiss();
            }

            @Override
            public void gallery() {
                mMyDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, SELECT_PHOTO);
            }

            @Override
            public void cancel() {
                mMyDialog.dismiss();
            }
        });

        /**
         * 拉去一条公告
         */
        mTeamNoticeController.getTeamNoticeLimit(Cache.team.getId(), 1);


    }


    /**
     * 判断管理权限的时候需要再重新拉取一遍user
     * @param users
     */
    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(TeamIntroduction.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() == 0)
                    Toast.makeText(TeamIntroduction.this, "error", Toast.LENGTH_SHORT).show();
                else {
                    Cache.user = users.get(0);
                    if (Cache.user.getTeamLevel() < 2) {
                        Toast.makeText(TeamIntroduction.this, "无管理员权限", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(TeamIntroduction.this, Manage.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        leaderTxt.setText("团长： " + Cache.team.getUser().getUsername());
        campusTxt.setText("学校： " + Cache.team.getCampus());
        collegeTxt.setText("专业： " + Cache.team.getCollege());
        sizeTxt.setText("规模： " + Cache.team.getTeamSize() + "人");
        scoreTxt.setText("平均分数： " + (double)Cache.team.getScore() / Cache.team.getTeamSize());
        lengthTxt.setText("平均里程： " + (double)Cache.team.getLength() / Cache.team.getTeamSize() + "公里");
        buildTimeTxt.setText("创建时间： " + Cache.team.getCreateTime().toString().substring(0, Cache.team.getCreateTime().toString().lastIndexOf(" ")));


        mFileController.getImgWithType(ImgNameUtil.getGroupHeadImgName(Cache.team.getId()), HEAD_IMG);
        mFileController.getImgWithType(ImgNameUtil.getTeamBackImgName(Cache.team.getId()), BACK_IMG);
        teamNameTxt.setText(Cache.team.getTeamName());
        teamRegisterNumTxt.setText(Cache.team.getRegisterNum());
        //add or delete button

        if (Cache.user.getTeam() == null || Cache.user.getTeam().getId() != Cache.team.getId()) {
            addBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);
        }else {

            if (Cache.user.getTeam().getUser().getId() == Cache.user.getId()) {
                addBtn.setVisibility(View.INVISIBLE);
                deleteBtn.setVisibility(View.INVISIBLE);
            }
        }
        //teamFunction
        if (Cache.user.getTeam() != null && Cache.user.getTeam().getId() == Cache.team.getId()) {
            teamFunction.setVisibility(View.VISIBLE);
        }else {
            teamFunction.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getImgWithTypeBack(Drawable drawable, int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) {
                    if (type == HEAD_IMG) teamImg.setImageDrawable(drawable);
                    else if (type == BACK_IMG) backImg.setImageDrawable(drawable);
                }
            }
        });
    }

    @Override
    public void getTeamNoticeLimitBack(List<TeamNotice> teamNoticeList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTeamNoticeList = teamNoticeList;
                if (mTeamNoticeList != null && mTeamNoticeList.size() != 0) {
                    noticeMsgTxt.setText(mTeamNoticeList.get(0).getNoticeMsg());
                }else {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                    layoutParams.leftMargin = DensityUtil.dip2px(TeamIntroduction.this, 20);
                    noticeTxt.setLayoutParams(layoutParams);
                }
            }
        });
    }

    @Override
    public void signOutTeamBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamIntroduction.this, res ? "已退出" : "退出失败", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(TeamIntroduction.this, TeamIntroduction.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void uploadBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TeamIntroduction.this, res ? "修改成功" : "修改失败", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(TeamIntroduction.this, TeamIntroduction.class);
                    startActivity(intent);
                }
            }
        });
    }
}
