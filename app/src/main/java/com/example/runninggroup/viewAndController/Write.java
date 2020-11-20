package com.example.runninggroup.viewAndController;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.example.runninggroup.R;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.controller.FriendCircleController;
import com.example.runninggroup.model.DaoDynamic;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.test.MyDialog;
import com.example.runninggroup.util.BitmapUtil;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.viewAndController.adapter.WriteAdapter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Write extends AppCompatActivity implements View.OnClickListener, FriendCircleController.FriendCircleControllerInterface, FileController.FileControllerInterface {
    RecyclerView imgRecy;
    Button releaseBtn;
    ImageView writeTurn;
    EditText msgEdt;
    String username;
    File mFile;
    Bitmap bitmap;
    private String img_src;
    private final int SELECT_PHOTO = 2;
    private static final int REQUEST_CODE = 1;
    private static final int MAX_SELECT_COUNT = 9;
    private ArrayList<String> paths = new ArrayList<>();
    private ArrayList<Drawable> drawables = new ArrayList<>();
    private WriteAdapter writeAdapter;
    private FriendCircleController mFriendCircleController = new FriendCircleController(this);
    private FileController mFileController = new FileController(this);
    private ArrayList<String> selected = new ArrayList<>();
    private MyDialog mMyDialog;


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
            case REQUEST_CODE:
                showContent(data);
                break;
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
//                                    inputStream.close();
//
//                                    String[] proj = {MediaStore.Images.Media.DATA};
//                                    CursorLoader loader = new CursorLoader(Write.this, uri, proj, null, null, null);
//                                    Cursor cursor = loader.loadInBackground();
//                                    if (cursor != null) {
//                                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                                        cursor.moveToFirst();
//
//                                        img_src = cursor.getString(column_index);//图片实际路径
//
//                                    }
//                                    cursor.close();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mImageView.setImageBitmap(bitmap);
//                                        }
//                                    });
//
//
//                                } catch (FileNotFoundException e) {
//                                    Log.e("Exception", e.getMessage(), e);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }).start();
//
//                        break;
//                }
//                break;


        }

    }

    private void initView() {
        releaseBtn = findViewById(R.id.write_release);
        writeTurn = findViewById(R.id.write_return);
        msgEdt = findViewById(R.id.write_msg);
        imgRecy = findViewById(R.id.img_recy);
        mMyDialog = new MyDialog(this);
        writeAdapter = new WriteAdapter(drawables);
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
//                AlertDialog.Builder builder = new AlertDialog.Builder(Write.this);
//                builder.setTitle("请选择头像：");
//                builder.setMessage("可以通过相机或者相册选取头像。");
//                builder.setPositiveButton("相册选取", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
////                        startActivityForResult(intent, SELECT_PHOTO);
//                        selectImg();
//                    }
//                });
//                builder.create();
//                builder.show();

            }

            @Override
            public void imgOnClick() {
                Toast.makeText(Write.this, "click", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write_return:
                Intent intent = new Intent(Write.this,MainInterface.class);
                startActivity(intent);
                break;
            case R.id.write_release:
                String circleMsg = msgEdt.getText().toString();
                mFriendCircleController.addFriendCircle(circleMsg, paths.size());

                break;

        }
    }

    private void showContent(Intent data) {
        if (data != null) {
            ArrayList<String> strings = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            paths.addAll(strings);
            if (strings != null && strings.size() > 0) {
                for (int i = 0; i < strings.size(); i++) {
                    drawables.add(Drawable.createFromPath(strings.get(i)));
                }
                writeAdapter.notifyDataSetChanged();
            }
        }




    }

    public void selectImg() {
        ImageSelector.builder()
                .useCamera(true) // 设置是否使用拍照
                .setSingle(false)  //设置是否单选
                .setMaxSelectCount(MAX_SELECT_COUNT - drawables.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                .setSelected(selected) // 把已选的图片传入默认选中。
                .start(this, REQUEST_CODE); // 打开相册
    }

    @Override
    public void addFriendCircleBack(String res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res ==  null || "0".equals(res)) Toast.makeText(Write.this, "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    mFileController.uploadMore(paths, Integer.parseInt(res));
                }
            }
        });
    }

    @Override
    public void uploadMoreBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Write.this, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(Write.this, MainInterface.class);
                    intent.putExtra("viewPagerNum", ConstantUtil.FRAGMENT_FRIEND_CIRCLE);
                    startActivity(intent);
                }
            }
        });
    }
}
