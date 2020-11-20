package com.example.runninggroup.viewAndController;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.controller.FriendRelationController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.viewAndController.adapter.DynamicAdapter;
import com.example.runninggroup.viewAndController.adapter.FriendMessageAdapter;
import com.example.runninggroup.viewAndController.adapter.MyPagerAdapter;
import com.example.runninggroup.viewAndController.fragment.FragmentDynamic;
import com.example.runninggroup.viewAndController.fragment.FragmentFriendManage;
import com.example.runninggroup.viewAndController.fragment.FragmentPersonalCard;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FriendMessage extends AppCompatActivity implements UserController.UserControllerInterface, View.OnClickListener, FriendRelationController.FriendRelationControllerInterface, FileController.FileControllerInterface {

    TextView aliasText,registerNumText,settingText;
    ImageView mImageView, backImg, userBackImg;
    private String alias;
    private UserController mUserController = new UserController(this);
    private FriendRelationController mFriendRelationController = new FriendRelationController(this);
    private FileController mFileController = new FileController(this);
    private ListView mListView;
    private Button addFriend;
    private int fromActivity;
    private final int SELECT_PHOTO = 2;
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private final int REQUESTCODE_CUTTING = 3;
    File file;
    Uri mImageUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendmessage);
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFileController.getImg(ImgNameUtil.getUserBackImgName(Cache.friend.getId()));
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
                    file = new File(appDir, ImgNameUtil.getUserBackImgName(Cache.user.getId()) + ".jpg");
                    //新建裁剪图片存放的uri
                    mImageUri = Uri.fromFile(file);
                    startPhotoZoom(uri);
                }
                break;
            case REQUESTCODE_CUTTING:
                mFileController.upload(file, ImgNameUtil.getUserBackImgName(Cache.user.getId()));
                break;


        }
    }

    private void initView() {
        alias = getIntent().getStringExtra("alias");
        fromActivity = getIntent().getIntExtra("fromActivity", ConstantUtil.MAIN_INTERFACE);
       aliasText = findViewById(R.id.alias);
       registerNumText = findViewById(R.id.registerNum);
       backImg = findViewById(R.id.back_img);
       addFriend = findViewById(R.id.add_friend);
        settingText = findViewById(R.id.setting);
        mImageView = findViewById(R.id.img);
        userBackImg = findViewById(R.id.userBackImg);
        mListView = findViewById(R.id.friend_message);
        mListView.setAdapter(new FriendMessageAdapter(getLayoutInflater(), Cache.friend));
        aliasText.setText(alias == null ? Cache.friend.getUsername() : alias);
        registerNumText.setText(Cache.friend.getRegisterNum() + "(" + Cache.friend.getUsername() + ")");
        //透明
        StatusBarUtils.setStatusBarFullTransparent(this);
        String temp =Cache.friend.getHeadImg();
        mUserController.getHeadImg(Cache.friend.getHeadImg());
        if (Cache.friend.getId() != Cache.user.getId()) {
            mFriendRelationController.isMyFriend();
        }



        setView();
    }
    private void initEvent() {
        mImageView.setImageResource(Cache.friend.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        backImg.setOnClickListener(this);
        settingText.setOnClickListener(this);
        addFriend.setOnClickListener(this);
        backImg.setOnClickListener(this);
        userBackImg.setOnClickListener(this);


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
                else Toast.makeText(FriendMessage.this, "图片为空", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                Intent intent = null;
                if (fromActivity == ConstantUtil.MAIN_INTERFACE) {
                    intent = new Intent(FriendMessage.this, MainInterface.class);
                    intent.putExtra("id",2);
                }
                else if (fromActivity == ConstantUtil.PERSON_SEARCH) {
                    intent = new Intent(FriendMessage.this, SearchActivity.class);
                }
                else if (fromActivity == ConstantUtil.MEMBER_SORT) {
                    intent = new Intent(FriendMessage.this, MemberSort.class);
                }

                startActivity(intent);
                break;
            case R.id.setting:
                break;

            case R.id.add_friend:
                Intent intent1 = new Intent(getApplicationContext(), AddFriendActivity.class);
                startActivity(intent1);
                break;
            case R.id.userBackImg:

                if (Cache.friend.getId() == Cache.user.getId()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FriendMessage.this);
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

    @Override
    public void isMyFriendBack(List<FriendRelation> friendRelationList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendRelationList == null)
                    Toast.makeText(FriendMessage.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (friendRelationList.size() == 0)  {
                    addFriend.setVisibility(View.VISIBLE);
                }
                else {
                    if (friendRelationList.get(0).getAlias() != null) aliasText.setText(friendRelationList.get(0).getAlias());
                 }
            }
        });
    }

    @Override
    public void uploadBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FriendMessage.this, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(FriendMessage.this, FriendMessage.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void getImgBack(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) userBackImg.setImageDrawable(drawable);
            }
        });
    }
}
