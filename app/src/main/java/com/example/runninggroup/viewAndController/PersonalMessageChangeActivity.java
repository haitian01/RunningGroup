package com.example.runninggroup.viewAndController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.view.MyDialog;

import java.io.File;

public class PersonalMessageChangeActivity extends AppCompatActivity implements UserController.UserControllerInterface {
    private RelativeLayout headItem;
    private MyDialog mMyDialog;
    private ImageView backImg;
    private final int SELECT_PHOTO = 2;
    private final int REQUESTCODE_CUTTING = 3;
    private TextView defineTxt, sexTxt;
    private EditText usernameEdt, signatureEdt;
    private RelativeLayout sexItem;
    File file;
    Uri mImageUri;
    private UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_message);
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Cache.user != null) {
            usernameEdt.setText(Cache.user.getUsername());
            usernameEdt.setHint(Cache.user.getUsername());
            sexTxt.setText(Cache.user.getSex() == 1 ? "男" : "女");
            signatureEdt.setText(Cache.user.getSignature() == null ? "" : Cache.user.getSignature());
        }
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
                    file = new File(appDir, ImgNameUtil.getUserHeadImgName(Cache.user.getId()) + ".jpg");
                    //新建裁剪图片存放的uri
                    mImageUri = Uri.fromFile(file);
                    startPhotoZoom(uri);
                }

                break;
            case REQUESTCODE_CUTTING:
                mUserController.changeHeadImg(file);
                break;

        }

    }

    private void initEvent() {
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
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });
        headItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDialog.show();
            }
        });
        sexItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexChooseDialog();
            }
        });
        defineTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cache.user != null) {
                    User user = new User();
                    user.setId(Cache.user.getId());
                    String sex = sexTxt.getText().toString();
                    String signature = signatureEdt.getText().toString();
                    String username = usernameEdt.getText().toString();
                    if ("男".equals(sex)) user.setSex(1);
                    else if ("女".equals(sex)) user.setSex(2);
                    if (!StringUtil.isStringNull(signature)) user.setSignature(signature);
                    if (!StringUtil.isStringNull(username)) user.setUsername(username);
                    mUserController.updateUser(user);
                }


            }
        });
    }

    /**
     * 更新回调
     * @param res
     */
    @Override
    public void updateUserBack(boolean res, User user) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res && user != null && Cache.user != null) {
                    Integer sex = user.getSex();
                    String signature = user.getSignature();
                    String username = user.getUsername();
                    if (sex != null && sex != 0) Cache.user.setSex(sex);
                    if (signature != null) Cache.user.setSignature(signature);
                    if (username != null) Cache.user.setUsername(username);

                }
               Intent intent = new Intent(PersonalMessageChangeActivity.this, MainInterface.class);
               startActivity(intent);
            }
        });
    }

    private void initView() {
        mMyDialog = new MyDialog(this);
        headItem = findViewById(R.id.head_item);
        backImg = findViewById(R.id.back);
        defineTxt = findViewById(R.id.define);
        usernameEdt = findViewById(R.id.username_edt);
        signatureEdt = findViewById(R.id.signature_edt);
        sexItem = findViewById(R.id.sex_item);
        sexTxt = findViewById(R.id.sex_txt);
    }
    private String[] sexArray = new String[]{"男", "女"};// 性别选择
    private void showSexChooseDialog() {
        if (Cache.user != null && Cache.user.getSex() != null) {
            AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
            builder3.setSingleChoiceItems(sexArray, Cache.user.getSex() == 1 ? 0 : 1, new DialogInterface.OnClickListener() {// 2默认的选中

                @Override
                public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                    // showToast(which+"");
                    sexTxt.setText(sexArray[which]);
                    dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                }
            });
            builder3.show();// 让弹出框显示
        }
    }

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
    @Override
    public void changeHeadImgBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res) {
                    Toast.makeText(PersonalMessageChangeActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PersonalMessageChangeActivity.this, MainInterface.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(PersonalMessageChangeActivity.this, "更换失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
