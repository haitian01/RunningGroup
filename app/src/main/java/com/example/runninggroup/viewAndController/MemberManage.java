package com.example.runninggroup.viewAndController;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.viewAndController.adapter.MemberManageAdapter;
import com.example.runninggroup.viewAndController.helper.MemberManageHelper;

import java.util.List;

public class MemberManage extends AppCompatActivity {
    ListView memberManageList;
    String username,group;
    List<MemberManageHelper> mList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membermanage);
        initView();
        initEvent();
    }

    private void initView() {

        memberManageList = findViewById(R.id.membermanage_list);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //拿到成员列表
                mList = DaoGroup.getMemberTitle(group);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        memberManageList.setAdapter(new MemberManageAdapter(getLayoutInflater(),mList));
    }
    private void initEvent() {
        memberManageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Membername = mList.get(position).getUsername();
                String title = (mList.get(position).getAdmin()==1) ? "管理员" : "成员";
//                switch (title){
//                    case "管理员":
//                        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
//                        builder.setTitle("解除管理员")
//                                .setMessage("你确定接触"+Membername+"的管理员权限？")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        //解除管理员
//
//                                    }
//                                })
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        //
//                                    }
//                                }).create();
//                        builder.show();
//
//                        break;
//                    case "成员":
//                        androidx.appcompat.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(MemberManage.this);
//                        builder1.setTitle("授予管理员")
//                                .setMessage("你确定授予"+Membername+"管理员权限？")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        //授予管理员
//
//                                    }
//                                })
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        //
//                                    }
//                                }).create();
//                        builder1.show();
//                        break;
//                }
            }
        });
    }
}
