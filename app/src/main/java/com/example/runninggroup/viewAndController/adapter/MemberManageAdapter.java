package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.TeamController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.PinYinUtil;
import com.example.runninggroup.viewAndController.MemberManage;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class MemberManageAdapter extends BaseAdapter implements UserController.UserControllerInterface, TeamController.TeamControllerInterface {
    private Activity mActivity;
    public User[] mUsers;
    private UserController mUserController = new UserController(this);
    private TeamController mTeamController = new TeamController(this);
    public MemberManageAdapter(Activity activity, List<User> userList) {
       mActivity = activity;
       mUsers = new User[userList.size()];
       for (int i = 0; i < mUsers.length; i++) {
           mUsers[i] = userList.get(i);
       }
        Arrays.sort(mUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getTeamLevel() == 3) return -1;
                if (o2.getTeamLevel() == 3) return 1;
                if (o1.getTeamLevel() > 1 && o2.getTeamLevel() > 1) return 0;
                if (o1.getTeamLevel() > 1) return -1;
                if (o2.getTeamLevel() > 1) return 1;
                char c1 = PinYinUtil.getPingYin(o1.getUsername()).charAt(0);
                char c2 = PinYinUtil.getPingYin(o2.getUsername()).charAt(0);
                if (c1 < 'a' || c1 > 'z') return 1;
                if (c2 < 'a' || c2 > 'z') return -1;
                if (c1 == c2) return 0;
                return c1 > c2 ? 1 : -1;
            }
        });
       for (int i = 0; i < mUsers.length; i++) {
           System.out.println(mUsers[i]);
       }
    }

    @Override
    public int getCount() {
        return mUsers.length;
    }

    @Override
    public Object getItem(int position) {
        return  mUsers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //判断converView是否为空
        ViewHolder viewHolder;

        convertView=mActivity.getLayoutInflater().inflate(R.layout.helper_member_manage,null);
        viewHolder=new ViewHolder();
        viewHolder.img=convertView.findViewById(R.id.img);
        viewHolder.username=convertView.findViewById(R.id.username);
        viewHolder.remark=convertView.findViewById(R.id.remark);
        viewHolder.txt=convertView.findViewById(R.id.txt);
        viewHolder.item=convertView.findViewById(R.id.item);

        convertView.setTag(viewHolder);

        User user = mUsers[position];
        viewHolder.username.setText(user.getUsername());
        viewHolder.img.setImageResource(user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        setImg(viewHolder, user.getId());
        viewHolder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                User user1 = user;
                //用户必须是管理员 && 操作用户不能是自己
                //自己是组长 || 操作的是非管理员
                if (Cache.user.getTeamLevel() > 1 && user1.getId() != Cache.user.getId() && (Cache.user.getId() == Cache.user.getTeam().getUser().getId() || user1.getTeamLevel() == 1)){
                    manage(user1);
                }
                return false;
            }
        });


        if (position == 0) {
            viewHolder.txt.setText("群主、管理员");
            viewHolder.remark.setVisibility(View.VISIBLE);
        }
        else if (user.getTeamLevel() > 1) {
            viewHolder.remark.setVisibility(View.GONE);
        }
        else if (user.getTeamLevel() == 1 && mUsers[position - 1].getTeamLevel() > 1) {
            char c = PinYinUtil.getPingYin(user.getUsername()).charAt(0);
            viewHolder.txt.setText(c >= 'a' && c <= 'z' ? c + "" : "#");
            viewHolder.remark.setVisibility(View.VISIBLE);
        }
        else if (PinYinUtil.getPingYin(user.getUsername()).charAt(0) != PinYinUtil.getPingYin(mUsers[position - 1].getUsername()).charAt(0)) {
            char c = PinYinUtil.getPingYin(user.getUsername()).charAt(0);
            viewHolder.txt.setText(c >= 'a' && c <= 'z' ? c + "" : "#");
            viewHolder.remark.setVisibility(View.VISIBLE);
        }else if (PinYinUtil.getPingYin(user.getUsername()).charAt(0) == PinYinUtil.getPingYin(mUsers[position - 1].getUsername()).charAt(0)){
            viewHolder.remark.setVisibility(View.GONE);
        }

        return convertView;

    }

    public void setImg (ViewHolder viewHolder, int user_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = ImgGet.getImg(ImgNameUtil.getUserHeadImgName(user_id));
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null) viewHolder.img.setImageDrawable(drawable);
                    }
                });
            }
        }).start();
    }

    //ViewHolder内部类
    class ViewHolder{
        public ImageView img;
        public TextView username;
        public RelativeLayout remark;
        public TextView txt;
        public RelativeLayout item;

    }


    private void manage(User user){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("成员管理")
                .setItems(new String[]{"踢出跑团","管理员权限"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //踢出跑团
                                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                                builder.setTitle("踢出成员").setMessage("你确定踢出" + "\"" + user.getUsername() + "\"").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                            mTeamController.removeUserFromTeam(user.getId(), user.getTeam().getId());
                                    }
                                }).create().show();

                                break;
                            case 1:
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
                                String s = user.getTeamLevel() == 2 ? "移除" : "授予";
                                builder1.setTitle(s + "管理员")
                                        .setMessage("你确定" + s + "\"" + user.getUsername() + "\"" +"管理员权限？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //授予/移除管理员
                                                mUserController.administrators(user);


                                            }
                                        }).create().show();




                                break;
                        }
                    }
                }).create();
        builder.show();
    }

    @Override
    public void removeUserFromTeamBack(boolean res) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(mActivity, MemberManage.class);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void administratorsBack(boolean res) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res) {
                    Intent intent = new Intent(mActivity, MemberManage.class);
                    mActivity.startActivity(intent);
                }
            }
        });
    }
}
