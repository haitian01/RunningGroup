package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.controller.TeamApplicationController;
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.TeamApplication;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class TeamApplicationAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    List<TeamApplication> mList;
    private Activity mActivity;
    private ViewHolder viewHolder1;
    private TeamApplicationController mTeamApplicationController;
    public TeamApplicationAdapter(LayoutInflater inflater, List<TeamApplication> list, Activity activity, TeamApplicationController.TeamApplicationControllerInterface teamApplicationControllerInterface) {
        mInflater = inflater;
        mList = list;
        mActivity = activity;
        mTeamApplicationController = new TeamApplicationController(teamApplicationControllerInterface);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int id = mList.get(position).getId();
        int state = mList.get(position).getState();
        User user = mList.get(position).getUser();

        String msg = mList.get(position).getApplicationMsg();
        convertView = mInflater.inflate(R.layout.helper_friend_application, null);
        viewHolder1 = new ViewHolder();
        viewHolder1.mImageView = convertView.findViewById(R.id.img);
        viewHolder1.usernameText = convertView.findViewById(R.id.username);
        viewHolder1.msgText = convertView.findViewById(R.id.msg);
        viewHolder1.stateText = convertView.findViewById(R.id.state);
        viewHolder1.btn = convertView.findViewById(R.id.btn);
        viewHolder1.btn1 = convertView.findViewById(R.id.btn1);
        //按钮事件
        viewHolder1.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTeamApplicationController.updateTeamApplication(id,2, user.getId());

            }
        });
        viewHolder1.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTeamApplicationController.updateTeamApplication(id,3, user.getId());

            }
        });
        convertView.setTag(viewHolder1);


        viewHolder1.mImageView.setImageResource(user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        setImg(viewHolder1, ImgNameUtil.getUserHeadImgName(user.getId()));
        if (state == 1) {
            //未处理
            viewHolder1.usernameText.setText(user.getUsername());
            viewHolder1.btn.setVisibility(View.VISIBLE);
            viewHolder1.btn1.setVisibility(View.VISIBLE);
            viewHolder1.msgText.setText(msg);


        }else if (state == 2) {
            //已经同意的
            viewHolder1.usernameText.setText(user.getUsername());
            viewHolder1.btn.setVisibility(View.INVISIBLE);
            viewHolder1.btn1.setVisibility(View.INVISIBLE);
            viewHolder1.stateText.setText("已同意");
            viewHolder1.msgText.setText(msg);

        }else if (state == 3) {
            //已拒绝
            viewHolder1.usernameText.setText(user.getUsername());
            viewHolder1.btn.setVisibility(View.INVISIBLE);
            viewHolder1.btn1.setVisibility(View.INVISIBLE);
            viewHolder1.stateText.setText("已拒绝");
            viewHolder1.msgText.setText(msg);

        }



        return convertView;
    }
    class ViewHolder {
        ImageView mImageView;
        TextView usernameText, msgText, stateText;
        Button btn,btn1;
    }

    public void setImg (ViewHolder viewHolder, String imgName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = FileDao.getImg(imgName);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null)
                            viewHolder.mImageView.setImageDrawable(drawable);
                    }
                });

            }
        }).start();
    }



}
