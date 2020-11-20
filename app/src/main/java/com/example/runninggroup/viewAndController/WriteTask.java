package com.example.runninggroup.viewAndController;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.runninggroup.R;
import com.example.runninggroup.controller.TeamTaskController;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.util.BitmapUtil;
import com.example.runninggroup.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class WriteTask extends AppCompatActivity implements View.OnClickListener, TeamTaskController.TeamTaskControllerInterface {
    EditText msgEdt;
    Button releaseBtn;
    String username,group,num,type;
    ImageView mImageView;
    File file;
    String path;
    Bitmap bitmap;
    private String img_src;
    private TeamTaskController mTeamTaskController = new TeamTaskController(this);
    private final int SELECT_PHOTO = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writetask);
        initView();
        initEvent();
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case SELECT_PHOTO:
//                switch (resultCode) {
//                    case RESULT_OK:
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Uri uri = data.getData();
//                                img_src = uri.getPath();//这是本机的图片路径
//
//                                ContentResolver cr = getContentResolver();
//                                try {
//                                    InputStream inputStream = cr.openInputStream(uri);
//                                    bitmap = BitmapFactory.decodeStream(inputStream);
//                                    try {
//                                        inputStream.close();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//
//
//                                    String[] proj = {MediaStore.Images.Media.DATA};
//                                    CursorLoader loader = new CursorLoader(WriteTask.this, uri, proj, null, null, null);
//                                    Cursor cursor = loader.loadInBackground();
//                                    if (cursor != null) {
//                                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                                        cursor.moveToFirst();
//
//                                        img_src = cursor.getString(column_index);//图片实际路径
//
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                mImageView.setImageBitmap(bitmap);
//                                            }
//                                        });
//
//                                    }
//                                    cursor.close();
//
//                                } catch (FileNotFoundException e) {
//                                    Log.e("Exception", e.getMessage(), e);
//                                }
//
//                            }
//                        }).start();
//
//                        break;
//                }
//                break;
//
//
//        }
//
//    }

    private void initView() {
        releaseBtn = findViewById(R.id.writetask_release);
        msgEdt = findViewById(R.id.writetask_msg);
        mImageView = findViewById(R.id.writetask_img);
        username = getIntent().getStringExtra("username");
        group = getIntent().getStringExtra("group");
        num = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
    }
    private void initEvent() {
        releaseBtn.setOnClickListener(this);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.writetask_release:
                String msg = msgEdt.getText().toString();
                if (StringUtil.isStringNull(msg))
                    Toast.makeText(this, "内容不可以为空", Toast.LENGTH_SHORT).show();
                else mTeamTaskController.addTeamTask(msg);
                break;
            case R.id.writetask_return:
                Intent intent = new Intent(WriteTask.this,Manage.class);
                startActivity(intent);
            case R.id.writetask_img:
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteTask.this);
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
        }
    }

    @Override
    public void addTeamTaskBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WriteTask.this, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(WriteTask.this, Manage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
