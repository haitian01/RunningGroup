package com.example.runninggroup.viewAndController;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.ImgUpload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public class ResetPersonalData extends AppCompatActivity implements View.OnClickListener {
    private Button mBtn_return,mBtn_resetData;
    private Button mBtn_changeData;
    private ImageView mIma_chosePic;
    private EditText mEd_oldPas;
    private EditText mEd_newPas;
    private EditText mEd_newPas_repeat;
    public static final int TAKE_CAMERA = 101;
    private Uri imageUri;
    private Context context;
    private Uri uri;
    String username;
    private String img_src;
    private final int SELECT_PHOTO = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_personal_data);
        initView();
        initEvent();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 2) {
//            // 从相册返回的数据
//            if (data != null) {
//                //得到图片的全路径
//                uri = data.getData();
//                mIma_chosePic.setImageURI(uri);
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri uri = data.getData();
                        img_src = uri.getPath();//这是本机的图片路径

                        ContentResolver cr = getContentResolver();
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            /* 将Bitmap设定到ImageView */
                            mIma_chosePic.setImageBitmap(bitmap);

                            String[] proj = {MediaStore.Images.Media.DATA};
                            CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
                            Cursor cursor = loader.loadInBackground();
                            if (cursor != null) {
                                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                cursor.moveToFirst();

                                img_src = cursor.getString(column_index);//图片实际路径

                            }
                            cursor.close();

                        } catch (FileNotFoundException e) {
                            Log.e("Exception", e.getMessage(), e);
                        }

                        break;
                }
                break;

        }

    }

    private void initView(){
        username = getIntent().getStringExtra("username");
        mBtn_return = findViewById(R.id.returnToMain);
        mBtn_resetData = findViewById(R.id.resetData);
        mIma_chosePic = findViewById(R.id.choseImage);

    }

    private void initEvent(){
        mBtn_return.setOnClickListener(this);
        mBtn_resetData.setOnClickListener(this);
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
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, SELECT_PHOTO);
                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.resetData:
                if(img_src != null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("TAG", ImgUpload.uploadImg(img_src,DaoUser.getUserHeadImgName(username)).toString());
                        }
                    }).start();

                }


                break;
//            case R.id:
//                break;
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.Q)
//
//    public static File uriToFileApiQ(Context context,Uri uri) {
//        File file = null;
//        //android10以上转换
//        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
//            file = new File(uri.getPath());
//        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
//            //把文件复制到沙盒目录
//            ContentResolver contentResolver = context.getContentResolver();
//            Cursor cursor = contentResolver.query(uri, null, null, null, null);
//            if (cursor.moveToFirst()) {
//                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                try {
//                    InputStream is = contentResolver.openInputStream(uri);
//                    File cache = new File(context.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
//                    FileOutputStream fos = new FileOutputStream(cache);
//                    FileUtils.copy(is, fos);
//                    file = cache;
//                    fos.close();
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return file;
//    }

}
