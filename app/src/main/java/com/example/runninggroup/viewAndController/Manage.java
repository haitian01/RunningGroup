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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.util.BitmapUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Manage extends AppCompatActivity implements View.OnClickListener {
    ListView groupmanageList;
    Intent mIntent;
    String username;
    String group;
    String num;
    String leader;
    private String img_src;
    private final int SELECT_PHOTO = 2;
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
                switch (resultCode) {
                    case RESULT_OK:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Uri uri = data.getData();
                                img_src = uri.getPath();//这是本机的图片路径
                                Log.v("本机路径：",img_src);
                                ContentResolver cr = getContentResolver();
                                try {
                                    InputStream inputStream = cr.openInputStream(uri);
                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    inputStream.close();
                                    /* 将Bitmap设定到ImageView */
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Manage.this);
                                            final View view1=getLayoutInflater().inflate(R.layout.helper_changehead,null);
                                            ImageView img = view1.findViewById(R.id.head_img);
                                            img.setImageBitmap(bitmap);

                                            builder.setMessage("确定更换？")
                                                    .setView(view1)
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    String path = BitmapUtil.saveMyBitmap(Manage.this,bitmap, DaoUser.getGroupHeadImgName(group));
                                                                    File file = new File(path);
                                                                    String result = ImgUpload.uploadFile(file, DaoUser.getGroupHeadImgName(group));
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(Manage.this, result, Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(Manage.this,GroupMessage.class);
                                                                            intent.putExtra("username",username);
                                                                            intent.putExtra("id",0);
                                                                            startActivity(intent);


                                                                        }
                                                                    });
                                                                }
                                                            }).start();
                                                        }
                                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });
                                            builder.show();
//                                            mIma_chosePic.setImageBitmap(bitmap);
                                        }
                                    });


                                    String[] proj = {MediaStore.Images.Media.DATA};
                                    CursorLoader loader = new CursorLoader(Manage.this, uri, proj, null, null, null);
                                    Cursor cursor = loader.loadInBackground();
                                    if (cursor != null) {
                                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                        cursor.moveToFirst();

                                        img_src = cursor.getString(column_index);//图片实际路径
                                        Log.v("实际路径：",img_src);

                                    }
                                    cursor.close();

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
        groupmanageList = findViewById(R.id.groupmanage_list);
        mIntent = getIntent();
        username = mIntent.getStringExtra("username");
        group = mIntent.getStringExtra("group");
        num = mIntent.getStringExtra("num");
        leader = mIntent.getStringExtra("leader");
    }
    private void initEvent() {
       groupmanageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                   case 0:
                       Intent intent = new Intent(Manage.this,WriteTask.class);
                       intent.putExtra("username",username);
                       intent.putExtra("group",group);
                       intent.putExtra("num",num);
                       intent.putExtra("type","task");
                       startActivity(intent);
                       break;
                   case 1:
                       Intent intent1 = new Intent(Manage.this,WriteTask.class);
                       intent1.putExtra("username",username);
                       intent1.putExtra("group",group);
                       intent1.putExtra("num",num);
                       intent1.putExtra("type","call");
                       startActivity(intent1);
                       break;
                   case 2:
                        Intent intent2 = new Intent(Manage.this,MemberManage.class);
                        intent2.putExtra("username",username);
                        intent2.putExtra("group",group);
                        intent2.putExtra("num",num);
                        startActivity(intent2);
                       break;
                   case 3:
                       if(username.equals(leader)){
                           AlertDialog.Builder builder = new AlertDialog.Builder(Manage.this);
                           builder.setTitle("解散跑团")
                                   .setMessage("你确定解散跑团？")
                                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           //解散跑团
                                           new Thread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   if(DaoGroup.dismissGroup(group)){
                                                       makeToast("解散成功");
                                                       Intent intent = new Intent(Manage.this,MainInterface.class);
                                                       intent.putExtra("username",username);
                                                       startActivity(intent);
                                                   }else {makeToast("解散失败");}
                                               }
                                           }).start();
                                       }
                                   })
                                   .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           //
                                       }
                                   }).create();
                           builder.show();
                       }else {
                           androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(Manage.this);
                           builder.setMessage("没有管理权限！").create();
                           builder.show();
                       }

                       break;
                   case 4:
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
               }
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

}
