package com.example.runninggroup.viewAndController;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;

public class Manage extends AppCompatActivity implements View.OnClickListener {
    ListView groupmanageList;
    Button releaseBtn;
    EditText msg;
    Intent mIntent;
    String username;
    String group;
    String num;
    String leader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        initView();
        initEvent();
    }

    private void initView() {
        groupmanageList = findViewById(R.id.groupmanage_list);
        mIntent = getIntent();
        username = mIntent.getStringExtra("username");
        group = mIntent.getStringExtra("group");
        num = mIntent.getStringExtra("num");
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
                       startActivity(intent);
                       break;
                   case 1:
                        Intent intent1 = new Intent(Manage.this,MemberManage.class);
                        intent1.putExtra("username",username);
                        intent1.putExtra("group",group);
                        intent1.putExtra("num",num);
                        startActivity(intent1);
                       break;
                   case 2:
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
                                                   Toast.makeText(Manage.this,"解散成功",Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Manage.this,MainInterface.class);
                                                    intent.putExtra("username",username);
                                                    startActivity(intent);
                                               }else {Toast.makeText(Manage.this,"解散成功",Toast.LENGTH_SHORT).show();}
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

}
