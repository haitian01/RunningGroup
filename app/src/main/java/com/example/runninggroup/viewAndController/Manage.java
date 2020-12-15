package com.example.runninggroup.viewAndController;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.controller.TeamController;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;

import java.io.File;

public class Manage extends AppCompatActivity implements View.OnClickListener , FileController.FileControllerInterface, TeamController.TeamControllerInterface {
    ListView groupManageList;
    Intent mIntent;
    String username;
    ImageView backImg;
    private final int SELECT_PHOTO = 2;
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private final int REQUESTCODE_CUTTING = 3;
    private FileController mFileController = new FileController(this);
    private TeamController mTeamController = new TeamController(this);
    File file;
    Uri mImageUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        initView();
        initEvent();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (data != null) {
                    //根据返回的data获取图片的uri
                    Uri uri = data.getData();
                    String sdCardDir = getExternalCacheDir().toString();
                    File appDir = new File(sdCardDir, "/GuanGuan/");
                    if (!appDir.exists()) appDir.mkdir();
                    file = new File(appDir, ImgNameUtil.getGroupHeadImgName(Cache.team.getId()) + ".jpg");
                    //新建裁剪图片存放的uri
                    mImageUri = Uri.fromFile(file);
                    startPhotoZoom(uri);
                }
                break;
            case REQUESTCODE_CUTTING:
                mFileController.upload(file,ImgNameUtil.getGroupHeadImgName(Cache.team.getId()));
                break;
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:

                break;

        }

    }
    private void initView() {
        groupManageList = findViewById(R.id.groupmanage_list);
        backImg = findViewById(R.id.back);
        mIntent = getIntent();
    }
    private void initEvent() {
       groupManageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                   case 0:
                       //发布公告
                       Intent intent = new Intent(Manage.this, WriteNotice.class);
                       startActivity(intent);
                       break;

                   case 1:
                       //成员管理
                       Intent intent2 = new Intent(Manage.this,MemberManage.class);
                       startActivity(intent2);
                       break;
                   case 2:
                       //更换头像
                       AlertDialog.Builder builder = new AlertDialog.Builder(Manage.this);
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
                       break;
                   case 3:
                       //编辑口号

                       androidx.appcompat.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(Manage.this);
                       View view1 = getLayoutInflater().inflate(R.layout.helper_slogan,null);
                       builder1.setView(view1)
                               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {


                                       EditText editText = view1.findViewById(R.id.write_slogan);
                                       String slogan = editText.getText().toString();
                                       if(StringUtil.isStringNull(slogan)){
                                           makeToast("口号不能为空");
                                       }else {
                                           Team team = new Team();
                                           team.setTeamSlogan(slogan);
                                           mTeamController.updateTeamSlogan(team);
                                       }

                                   }
                               })
                               .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               }).create().show();
                       break;

                   case 4:
                       //申请消息
                       Intent intent3 = new Intent(Manage.this, TeamApplicationActivity.class);
                       startActivity(intent3);

                       break;
                   case 5:
                       //解散跑团
                       if(Cache.user.getId() == Cache.user.getTeam().getUser().getId()){
                           AlertDialog.Builder builder2 = new AlertDialog.Builder(Manage.this);
                           builder2.setTitle("解散跑团")
                                   .setMessage("你确定解散跑团？")
                                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           //解散跑团
                                           mTeamController.deleteTeam();

                                       }
                                   }).create();
                           builder2.show();
                       }else {
                           androidx.appcompat.app.AlertDialog.Builder builder2 = new AlertDialog.Builder(Manage.this);
                           builder2.setMessage("没有管理权限！").create();
                           builder2.show();
                       }
                       break;

               }
           }
       });
       backImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               WindowsEventUtil.systemBack();
           }
       });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }

    }
    public void makeToast(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Manage.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void uploadBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Manage.this, res ? "success" : "error", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(Manage.this, TeamIntroduction.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void updateTeamSloganBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Manage.this, res ? "success" : "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteTeamBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Manage.this, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(Manage.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
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

}
