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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class TeamApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        void acceptOnClick (InnerHolder viewHolder);
        void refuseOnClick(InnerHolder  viewHolder);
        void deleteOnClick (int position);
    }
    public void setBtnOnClickListener (BtnOnClickListener btnOnClickListener) {
        mBtnOnClickListener = btnOnClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.helper_friend_application, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) {
            InnerHolder viewHolder1 = (InnerHolder) holder;
            int state = mList.get(position).getState();
            User user = mList.get(position).getUser();
            String msg = mList.get(position).getApplicationMsg();
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
            viewHolder1.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBtnOnClickListener != null) mBtnOnClickListener.deleteOnClick(position);
                }
            });
        }



    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public TeamApplication mTeamApplication;
        public ImageView mImageView;
        public TextView usernameText, msgText, stateText;
        public LinearLayout delete;
        public Button acceptBtn,refuseBtn;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }
        public void initView () {
            mImageView = itemView.findViewById(R.id.img);
            usernameText = itemView.findViewById(R.id.username);
            msgText = itemView.findViewById(R.id.msg);
            stateText = itemView.findViewById(R.id.state);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            refuseBtn = itemView.findViewById(R.id.refuseBtn);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public void setImg (InnerHolder viewHolder, String imgName) {
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
