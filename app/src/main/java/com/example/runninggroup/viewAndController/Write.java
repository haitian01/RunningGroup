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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoDynamic;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.util.BitmapUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Write extends AppCompatActivity implements View.OnClickListener {
    Button releaseBtn;
    TextView returnText;
    EditText msgEdt;
    String username;
    ImageView mImageView;
    File mFile;
    Bitmap bitmap;
    private String img_src;
    private final int SELECT_PHOTO = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
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
                                    CursorLoader loader = new CursorLoader(Write.this, uri, proj, null, null, null);
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
        releaseBtn = findViewById(R.id.write_release);
        returnText = findViewById(R.id.write_return);
        msgEdt = findViewById(R.id.write_msg);
        mImageView = findViewById(R.id.write_img);
    }

    private void initEvent() {
        returnText.setOnClickListener(this);
        releaseBtn.setOnClickListener(this);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write_return:
                Intent intent = new Intent(Write.this,MainInterface.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.write_release:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long dynamic_time = System.currentTimeMillis();
                        //发表动态
                        String result = DaoDynamic.writeDynamic(username,msgEdt.getText().toString(),dynamic_time);
                        try {
                            String path = BitmapUtil.saveMyBitmap(Write.this,bitmap,DaoUser.getDynamicImgName(username,dynamic_time));
                            mFile = new File(path);
                            ImgUpload.uploadFile(mFile,DaoUser.getDynamicImgName(username,dynamic_time));
                        }catch (Exception e){
                            makeToast("图片发表失败");
                        }

                        if("SUCCESS".equals(result)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Write.this,"发表成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Write.this,"发表失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.write_img:
                AlertDialog.Builder builder = new AlertDialog.Builder(Write.this);
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
    private void makeToast(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Write.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
