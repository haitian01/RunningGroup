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
    String leaderName;
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
        username = getIntent().getStringExtra("username");
        group = getIntent().getStringExtra("group");

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                leaderName = DaoGroup.getLeader(group);
            }
        }).start();
    }
    private void initEvent() {
        memberManageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String memberName = mList.get(position).getUsername();
                final String admin = (mList.get(position).getAdmin()==1) ? "管理员" : "成员";


                if(username.equals(leaderName)){
                    androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
                    builder.setTitle("成员管理")
                            .setItems(new String[]{"踢出跑团","管理员权限"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            //踢出跑团
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if(DaoGroup.removeSb(group,memberName)){
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(MemberManage.this, "踢出成功", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }else {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(MemberManage.this, "踢出失败", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }).start();

                                            break;
                                        case 1:
                                            switch (admin){
                                                case "管理员":
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
                                                    builder.setTitle("解除管理员")
                                                            .setMessage("你确定接触"+memberName+"的管理员权限？")
                                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    //解除管理员
                                                                    new Thread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if(DaoGroup.setAdmin(group,memberName,0)){
                                                                                runOnUiThread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        Toast.makeText(MemberManage.this, "解除成功", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                            }else {
                                                                                runOnUiThread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        Toast.makeText(MemberManage.this, "解除失败", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                            }
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
                                                case "成员":
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MemberManage.this);
                                                    builder1.setTitle("授予管理员")
                                                            .setMessage("你确定授予"+memberName+"管理员权限？")
                                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    //授予管理员
                                                                    new Thread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if(DaoGroup.setAdmin(group,memberName,1)){
                                                                                runOnUiThread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        Toast.makeText(MemberManage.this, "授予成功", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                            }else {
                                                                                runOnUiThread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        Toast.makeText(MemberManage.this, "授予失败", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                            }
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
                                                    builder1.show();
                                                    break;
                                            }
                                            break;
                                    }
                                }
                            }).create();
                    builder.show();
                }else {
                    if(admin.equals("管理员")){
                        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
                        builder.setMessage("没有管理权限！").create();
                        builder.show();

                    }else {
                        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
                        builder.setTitle("成员管理")
                                .setItems(new String[]{"踢出跑团","管理员权限"}, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case 0:
                                                //踢出跑团
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if(DaoGroup.removeSb(group,memberName)){
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(MemberManage.this, "踢出成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }else {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(MemberManage.this, "踢出失败", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }).start();

                                                break;
                                            case 1:
                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MemberManage.this);
                                                builder1.setTitle("授予管理员")
                                                        .setMessage("你确定授予"+memberName+"管理员权限？")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                //授予管理员
                                                                new Thread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        if(DaoGroup.setAdmin(group,memberName,1)){
                                                                            runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    Toast.makeText(MemberManage.this, "授予成功", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }else {
                                                                            runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    Toast.makeText(MemberManage.this, "授予失败", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }
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
                                                builder1.show();


                                                break;
                                        }
                                    }
                                }).create();
                        builder.show();
                    }
                }



            }
        });
    }
}
