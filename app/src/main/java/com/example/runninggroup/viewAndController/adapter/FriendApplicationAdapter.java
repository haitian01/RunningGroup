package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
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
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.pojo.FriendApplication;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class FriendApplicationAdapter extends RecyclerView.Adapter<FriendApplicationAdapter.InnerHolder> {
    LayoutInflater mInflater;
    List<FriendApplication> mList;
    private Activity mActivity;
    private FriendApplicationController mFriendApplicationController;
    private OnButtonClickListener mOnButtonClickListener;
    public FriendApplicationAdapter(LayoutInflater inflater, List<FriendApplication> list, Activity activity) {
        mInflater = inflater;
        mList = list;
        mActivity = activity;
    }
    public interface OnButtonClickListener {
        void accept(int position);
        void refuse(int position);
        //侧滑拒绝
        void delete(int position);
    }
    public void setOnButtonClickListener (OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }


    @NonNull
    @Override
    public FriendApplicationAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.helper_friend_application, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendApplicationAdapter.InnerHolder holder, int position) {
        int state = mList.get(position).getState();
        User from = mList.get(position).getFrom();
        User to = mList.get(position).getTo();
        String msg = mList.get(position).getApplicationMsg();
        //按钮事件
        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null) mOnButtonClickListener.accept(position);

            }
        });
        holder.refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null) mOnButtonClickListener.refuse(position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null) mOnButtonClickListener.delete(position);
            }
        });
        if (from.getId() == Cache.user.getId()) {
            holder.mImageView.setImageResource(to.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            setImg(holder, ImgNameUtil.getUserHeadImgName(to.getId()));
            if (state == 1) {
                //以发送申请，等待验证
                holder.usernameText.setText(to.getUsername());
                holder.acceptBtn.setVisibility(View.INVISIBLE);
                holder.refuseBtn.setVisibility(View.INVISIBLE);
                holder.stateText.setText("等待验证");
                holder.msgText.setText("已发送验证信息");


            }else if (state == 2) {
                //对方已同意
                holder.usernameText.setText(to.getUsername());
                holder.acceptBtn.setVisibility(View.INVISIBLE);
                holder.refuseBtn.setVisibility(View.INVISIBLE);
                holder.stateText.setText("对方已同意");
                holder.msgText.setText("已发送验证信息");

            }else if (state == 3) {
                //对方已拒绝
                holder.usernameText.setText(to.getUsername());
                holder.acceptBtn.setVisibility(View.INVISIBLE);
                holder.refuseBtn.setVisibility(View.INVISIBLE);
                holder.stateText.setText("对方已拒绝");
                holder.msgText.setText("已发送验证信息");

            }
        }
        //别人申请的
        if (to.getId() == Cache.user.getId()) {
            holder.mImageView.setImageResource(from.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            setImg(holder, ImgNameUtil.getUserHeadImgName(from.getId()));
            if (state == 1) {
                //有验证消息和同意按钮
                holder.usernameText.setText(from.getUsername());
                holder.acceptBtn.setVisibility(View.VISIBLE);
                holder.refuseBtn.setVisibility(View.VISIBLE);
                holder.stateText.setVisibility(View.INVISIBLE);
                holder.msgText.setText("对方留言： " + msg);

            }else if (state == 2) {
                //验证消息和已同意
                holder.usernameText.setText(from.getUsername());
                holder.acceptBtn.setVisibility(View.INVISIBLE);
                holder.refuseBtn.setVisibility(View.INVISIBLE);
                holder.stateText.setVisibility(View.VISIBLE);
                holder.stateText.setText("已同意");
                holder.msgText.setText("对方留言： " + msg);

            }else if (state == 3) {
                //验证消息和已拒绝
                holder.usernameText.setText(from.getUsername());
                holder.acceptBtn.setVisibility(View.INVISIBLE);
                holder.refuseBtn.setVisibility(View.INVISIBLE);
                holder.stateText.setVisibility(View.VISIBLE);
                holder.stateText.setText("已拒绝");
                holder.msgText.setText("对方留言： " + msg);

            }
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
    class InnerHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView usernameText, msgText, stateText;
        Button acceptBtn,refuseBtn;
        LinearLayout delete;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }
        private void initView() {
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
