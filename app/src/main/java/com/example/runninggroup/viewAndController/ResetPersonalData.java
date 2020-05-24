package com.example.runninggroup.viewAndController;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.runninggroup.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public class ResetPersonalData extends AppCompatActivity implements View.OnClickListener {
    private Button mBtn_return;
    private Button mBtn_changeData;
    private ImageView mIma_chosePic;
    private EditText mEd_oldPas;
    private EditText mEd_newPas;
    private EditText mEd_newPas_repeat;
    public static final int TAKE_CAMERA = 101;
    private Uri imageUri;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_personal_data);
        initView();
        initEvent();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                //得到图片的全路径
                Uri uri = data.getData();
                mIma_chosePic.setImageURI(uri);
            }
        }
    }

    private void initView(){
        mBtn_return = findViewById(R.id.returnToMain);
        mIma_chosePic = findViewById(R.id.choseImage);
    }

    private void initEvent(){
        mBtn_return.setOnClickListener(this);
        mIma_chosePic.setOnClickListener(this);
        context = ResetPersonalData.this;
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.returnToMain:
                final Intent intent = new Intent(ResetPersonalData.this, MainInterface.class);
                startActivity(intent);
            break;
            case R.id.choseImage:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择头像：");
                builder.setMessage("可以通过相机或者相册选取头像。");
                builder.setPositiveButton("相册选取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                       intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                       startActivityForResult(intent1,2);
                    }
                });
                builder.create();
                builder.show();
                break;
//            case R.id:
//                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)

    public static File uriToFileApiQ(Context context,Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(context.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

}
