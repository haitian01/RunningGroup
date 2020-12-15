package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FriendApplicationController;
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class FriendApplicationAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    List<FriendApplication> mList;
    private Activity mActivity;
    private ViewHolder viewHolder1;
    private FriendApplicationController mFriendApplicationController;
    public FriendApplicationAdapter(LayoutInflater inflater, List<FriendApplication> list, Activity activity, FriendApplicationController.FriendApplicationControllerInterface friendApplicationControllerInterface) {
        mInflater = inflater;
        mList = list;
        mActivity = activity;
        mFriendApplicationController =   new FriendApplicationController(friendApplicationControllerInterface);
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
        int state = mList.get(position).getState();
//        User user = mList.get(position).getUser();
        User from = mList.get(position).getFrom();
        User to = mList.get(position).getTo();
        String msg = mList.get(position).getApplicationMsg();
        convertView = mInflater.inflate(R.layout.helper_friend_application, null);
        viewHolder1 = new ViewHolder();
        viewHolder1.mImageView = convertView.findViewById(R.id.img);
        viewHolder1.usernameText = convertView.findViewById(R.id.username);
        viewHolder1.msgText = convertView.findViewById(R.id.msg);
        viewHolder1.stateText = convertView.findViewById(R.id.state);
        viewHolder1.acceptBtn = convertView.findViewById(R.id.acceptBtn);
        viewHolder1.refuseBtn = convertView.findViewById(R.id.refuseBtn);
        //按钮事件
        viewHolder1.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mActivity, "同意", Toast.LENGTH_SHORT).show();
                FriendApplication friendApplication = new FriendApplication();
                friendApplication.setFrom(from);
                friendApplication.setTo(to);
                friendApplication.setState(2);
                mFriendApplicationController.agreeToRefuse(friendApplication);
            }
        });
        viewHolder1.refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mActivity, "拒绝", Toast.LENGTH_SHORT).show();
                FriendApplication friendApplication = new FriendApplication();
                friendApplication.setFrom(from);
                friendApplication.setTo(to);
                friendApplication.setState(3);
                mFriendApplicationController.agreeToRefuse(friendApplication);
            }
        });
        convertView.setTag(viewHolder1);
        //自己申请的
        if (from.getId() == Cache.user.getId()) {
            viewHolder1.mImageView.setImageResource(to.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            setImg(viewHolder1, ImgNameUtil.getUserHeadImgName(to.getId()));
            if (state == 1) {
                //以发送申请，等待验证
                viewHolder1.usernameText.setText(to.getUsername());
                viewHolder1.acceptBtn.setVisibility(View.INVISIBLE);
                viewHolder1.refuseBtn.setVisibility(View.INVISIBLE);
                viewHolder1.stateText.setText("等待验证");
                viewHolder1.msgText.setText("已发送验证信息");


            }else if (state == 2) {
                //对方已同意
                viewHolder1.usernameText.setText(to.getUsername());
                viewHolder1.acceptBtn.setVisibility(View.INVISIBLE);
                viewHolder1.refuseBtn.setVisibility(View.INVISIBLE);
                viewHolder1.stateText.setText("对方已同意");
                viewHolder1.msgText.setText("已发送验证信息");

            }else if (state == 3) {
                //对方已拒绝
                viewHolder1.usernameText.setText(to.getUsername());
                viewHolder1.acceptBtn.setVisibility(View.INVISIBLE);
                viewHolder1.refuseBtn.setVisibility(View.INVISIBLE);
                viewHolder1.stateText.setText("对方已拒绝");
                viewHolder1.msgText.setText("已发送验证信息");

            }
        }
        //别人申请的
        if (to.getId() == Cache.user.getId()) {
            viewHolder1.mImageView.setImageResource(from.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            setImg(viewHolder1, ImgNameUtil.getUserHeadImgName(from.getId()));
            if (state == 1) {
                //有验证消息和同意按钮
                viewHolder1.usernameText.setText(from.getUsername());
                viewHolder1.acceptBtn.setVisibility(View.VISIBLE);
                viewHolder1.refuseBtn.setVisibility(View.VISIBLE);
                viewHolder1.stateText.setVisibility(View.INVISIBLE);
                viewHolder1.msgText.setText("对方留言： " + msg);

            }else if (state == 2) {
                //验证消息和已同意
                viewHolder1.usernameText.setText(from.getUsername());
                viewHolder1.acceptBtn.setVisibility(View.INVISIBLE);
                viewHolder1.refuseBtn.setVisibility(View.INVISIBLE);
                viewHolder1.stateText.setVisibility(View.VISIBLE);
                viewHolder1.stateText.setText("已同意");
                viewHolder1.msgText.setText("对方留言： " + msg);

            }else if (state == 3) {
                //验证消息和已拒绝
                viewHolder1.usernameText.setText(from.getUsername());
                viewHolder1.acceptBtn.setVisibility(View.INVISIBLE);
                viewHolder1.refuseBtn.setVisibility(View.INVISIBLE);
                viewHolder1.stateText.setVisibility(View.VISIBLE);
                viewHolder1.stateText.setText("已拒绝");
                viewHolder1.msgText.setText("对方留言： " + msg);

            }
        }

        return convertView;
    }
    class ViewHolder {
        ImageView mImageView;
        TextView usernameText, msgText, stateText;
        Button acceptBtn,refuseBtn;
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
