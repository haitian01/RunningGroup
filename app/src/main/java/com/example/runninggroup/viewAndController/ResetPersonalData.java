package com.example.runninggroup.viewAndController;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.runninggroup.R;

import java.io.File;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_personal_data);
        initView();
        initEvent();
    }

    private void initView(){
        mBtn_return = findViewById(R.id.returnToMain);
        mIma_chosePic = findViewById(R.id.choseImage);
    }

    private void initEvent(){
        mBtn_return.setOnClickListener(this);
        mIma_chosePic.setOnClickListener(this);
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
                        File tempFile = new File(Environment.getExternalStorageState(), "PHOTO_FILE_NAME");
                    }
                });
                builder.create();
                builder.show();
                break;
//            case R.id:
//                break;
        }
    }

}
