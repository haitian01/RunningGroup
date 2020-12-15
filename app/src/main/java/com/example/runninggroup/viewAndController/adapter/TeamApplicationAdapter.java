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
import android.widget.Toast;

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
    private BtnOnClickListener mBtnOnClickListener;
    public TeamApplicationAdapter(LayoutInflater inflater, List<TeamApplication> list, Activity activity) {
        mInflater = inflater;
        mList = list;
        mActivity = activity;
    }

    public interface BtnOnClickListener {
        void acceptOnClick (ViewHolder viewHolder);
        void refuseOnClick(ViewHolder  viewHolder);
    }
    public void setBtnOnClickListener (BtnOnClickListener btnOnClickListener) {
        mBtnOnClickListener = btnOnClickListener;
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
        User user = mList.get(position).getUser();

        String msg = mList.get(position).getApplicationMsg();
        convertView = mInflater.inflate(R.layout.helper_friend_application, null);
        ViewHolder viewHolder1 = new ViewHolder();
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
                if (mBtnOnClickListener != null) mBtnOnClickListener.acceptOnClick(viewHolder1);

            }
        });
        viewHolder1.refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnOnClickListener != null) mBtnOnClickListener.refuseOnClick(viewHolder1);

            }
        });
        convertView.setTag(viewHolder1);


        viewHolder1.mImageView.setImageResource(user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        viewHolder1.mTeamApplication = mList.get(position);
        setImg(viewHolder1, ImgNameUtil.getUserHeadImgName(user.getId()));
        if (state == 1) {
            //未处理
            viewHolder1.usernameText.setText(user.getUsername());
            viewHolder1.acceptBtn.setVisibility(View.VISIBLE);
            viewHolder1.refuseBtn.setVisibility(View.VISIBLE);
            viewHolder1.msgText.setText(msg);


        }else if (state == 2) {
            //已经同意的
            viewHolder1.usernameText.setText(user.getUsername());
            viewHolder1.acceptBtn.setVisibility(View.INVISIBLE);
            viewHolder1.refuseBtn.setVisibility(View.INVISIBLE);
            viewHolder1.stateText.setText("已同意");
            viewHolder1.msgText.setText(msg);

        }else if (state == 3) {
            //已拒绝
            viewHolder1.usernameText.setText(user.getUsername());
            viewHolder1.acceptBtn.setVisibility(View.INVISIBLE);
            viewHolder1.refuseBtn.setVisibility(View.INVISIBLE);
            viewHolder1.stateText.setText("已拒绝");
            viewHolder1.msgText.setText(msg);

        }



        return convertView;
    }
    public class ViewHolder {
        public TeamApplication mTeamApplication;
        public ImageView mImageView;
        public TextView usernameText, msgText, stateText;
        public Button acceptBtn,refuseBtn;
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
