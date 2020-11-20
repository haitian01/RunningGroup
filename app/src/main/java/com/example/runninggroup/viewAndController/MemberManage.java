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
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.viewAndController.adapter.MemberAdapter;
import com.example.runninggroup.viewAndController.adapter.MemberManageAdapter;
import com.example.runninggroup.viewAndController.helper.MemberManageHelper;

import java.util.ArrayList;
import java.util.List;

public class MemberManage extends AppCompatActivity implements UserController.UserControllerInterface {

    ListView memberListView;
    List<User> userList = new ArrayList<>();
    private UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membermanage);
        initView();
        initEvent();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        User user = new User();
        Team team = new Team();
        team.setId(Cache.user.getTeam().getId());
        user.setTeam(team);
        mUserController.selectUserByUser(user);
    }

    private void initView() {
        memberListView = findViewById(R.id.memberListView);
        memberListView.setAdapter(new MemberManageAdapter(this,userList));


    }
    private void initEvent() {

    }

    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(MemberManage.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() == 0)
                    Toast.makeText(MemberManage.this, "error", Toast.LENGTH_SHORT).show();
                else {
                    userList = users;
                    memberListView.setAdapter(new MemberManageAdapter(MemberManage.this, userList));
                }
            }
        });
    }

    //    //成员管理
//    private void manage(final String memberName){
//        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
//        builder.setTitle("成员管理")
//                .setItems(new String[]{"踢出跑团","管理员权限"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:
//                                //踢出跑团
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if(DaoGroup.removeSb(group,memberName)){
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    Toast.makeText(MemberManage.this, "踢出成功", Toast.LENGTH_SHORT).show();
//                                                    updateView();
//                                                }
//                                            });
//                                        }else {
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    Toast.makeText(MemberManage.this, "踢出失败", Toast.LENGTH_SHORT).show();
//                                                    updateView();
//                                                }
//                                            });
//                                        }
//                                    }
//                                }).start();
//
//                                break;
//                            case 1:
//                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MemberManage.this);
//                                builder1.setTitle("授予管理员")
//                                        .setMessage("你确定授予"+memberName+"管理员权限？")
//                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                //授予管理员
//                                                new Thread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        if(DaoGroup.setAdmin(group,memberName,1)){
//                                                            runOnUiThread(new Runnable() {
//                                                                @Override
//                                                                public void run() {
//                                                                    Toast.makeText(MemberManage.this, "授予成功", Toast.LENGTH_SHORT).show();
//                                                                    updateView();
//                                                                }
//                                                            });
//                                                        }else {
//                                                            runOnUiThread(new Runnable() {
//                                                                @Override
//                                                                public void run() {
//                                                                    Toast.makeText(MemberManage.this, "授予失败", Toast.LENGTH_SHORT).show();
//                                                                    updateView();
//                                                                }
//                                                            });
//                                                        }
//                                                    }
//                                                }).start();
//
//                                            }
//                                        })
//                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                //
//                                            }
//                                        }).create();
//                                builder1.show();
//
//
//                                break;
//                        }
//                    }
//                }).create();
//        builder.show();
//    }
//    //群主管理
//    private void leaderManage(final String admin,final String memberName){
//
//        if (! memberName.equals(leaderName)) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
//            builder.setTitle("成员管理")
//                    .setItems(new String[]{"踢出跑团","管理员权限"}, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which){
//                                case 0:
//                                    //踢出跑团
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if(DaoGroup.removeSb(group,memberName)){
//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        Toast.makeText(MemberManage.this, "踢出成功", Toast.LENGTH_SHORT).show();
//                                                        updateView();
//                                                    }
//                                                });
//                                            }else {
//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        Toast.makeText(MemberManage.this, "踢出失败", Toast.LENGTH_SHORT).show();
//                                                        updateView();
//                                                    }
//                                                });
//                                            }
//                                        }
//                                    }).start();
//
//                                    break;
//                                case 1:
//                                    switch (admin){
//                                        case "管理员":
//                                            AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
//                                            builder.setTitle("解除管理员")
//                                                    .setMessage("你确定解除"+memberName+"的管理员权限？")
//                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            //解除管理员
//                                                            new Thread(new Runnable() {
//                                                                @Override
//                                                                public void run() {
//                                                                    if(DaoGroup.setAdmin(group,memberName,0)){
//                                                                        runOnUiThread(new Runnable() {
//                                                                            @Override
//                                                                            public void run() {
//                                                                                Toast.makeText(MemberManage.this, "解除成功", Toast.LENGTH_SHORT).show();
//                                                                                updateView();
//                                                                            }
//                                                                        });
//                                                                    }else {
//                                                                        runOnUiThread(new Runnable() {
//                                                                            @Override
//                                                                            public void run() {
//                                                                                Toast.makeText(MemberManage.this, "解除失败", Toast.LENGTH_SHORT).show();
//                                                                                updateView();
//                                                                            }
//                                                                        });
//                                                                    }
//                                                                }
//                                                            }).start();
//
//                                                        }
//                                                    })
//                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            //
//                                                        }
//                                                    }).create();
//                                            builder.show();
//
//                                            break;
//                                        case "成员":
//                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MemberManage.this);
//                                            builder1.setTitle("授予管理员")
//                                                    .setMessage("你确定授予"+memberName+"管理员权限？")
//                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            //授予管理员
//                                                            new Thread(new Runnable() {
//                                                                @Override
//                                                                public void run() {
//                                                                    if(DaoGroup.setAdmin(group,memberName,1)){
//                                                                        runOnUiThread(new Runnable() {
//                                                                            @Override
//                                                                            public void run() {
//                                                                                Toast.makeText(MemberManage.this, "授予成功", Toast.LENGTH_SHORT).show();
//                                                                                updateView();
//                                                                            }
//                                                                        });
//                                                                    }else {
//                                                                        runOnUiThread(new Runnable() {
//                                                                            @Override
//                                                                            public void run() {
//                                                                                Toast.makeText(MemberManage.this, "授予失败", Toast.LENGTH_SHORT).show();
//                                                                                updateView();
//                                                                            }
//                                                                        });
//                                                                    }
//                                                                }
//                                                            }).start();
//
//                                                        }
//                                                    })
//                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            //
//                                                        }
//                                                    }).create();
//                                            builder1.show();
//                                            break;
//                                    }
//                                    break;
//                            }
//                        }
//                    }).create();
//            builder.show();
//        }
//        else {
//            unadmintted();
//        }
//    }
    //无管理员权限提示
    private void unadmintted(){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MemberManage.this);
        builder.setMessage("没有管理权限！").create();
        builder.show();
    }

}
