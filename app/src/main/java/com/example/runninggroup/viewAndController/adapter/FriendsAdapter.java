package com.example.runninggroup.viewAndController.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.R;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public LayoutInflater mInflater;
    public List<FriendRelation> mList;
    public Activity mActivity;
    public OnItemClickListener mOnItemClickListener;
    public FriendsAdapter(LayoutInflater inflater, List<FriendRelation> list, Activity activity) {

        mInflater = inflater;
        mList = list;
        mActivity = activity;
    }
    public interface OnItemClickListener {
        void click(int position);
    }
    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.helper_friend, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) {
            InnerHolder viewHolder = (InnerHolder)holder;
            viewHolder.img.setImageResource(mList.get(position).getFriend().getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            setImg(viewHolder, ImgNameUtil.getUserHeadImgName(mList.get(position).getFriend().getId()));
            viewHolder.name.setText(mList.get(position).getAlias() != null ? mList.get(position).getAlias() : mList.get(position).getFriend().getUsername());
            viewHolder.group.setText(mList.get(position).getFriend().getTeam() == null ? "无" : mList.get(position).getFriend().getTeam().getTeamName());
            viewHolder.signature.setText(mList.get(position).getFriend().getSignature() == null ? "" : mList.get(position).getFriend().getSignature());
            if (position == 0) viewHolder.friendItem.setBackgroundColor(Color.parseColor("#f1f1f1"));
            viewHolder.friendItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) mOnItemClickListener.click(position);
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



    class InnerHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView name;
        public TextView group;
        public TextView length;
        public TextView score;
        public TextView signature;
        public RelativeLayout friendItem;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }
        private void initView () {
            img=itemView.findViewById(R.id.img);
            name=itemView.findViewById(R.id.name);
            group=itemView.findViewById(R.id.group);
            length=itemView.findViewById(R.id.length);
            score=itemView.findViewById(R.id.score);
            friendItem = itemView.findViewById(R.id.friend_item);
            signature = itemView.findViewById(R.id.signature);
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
                        viewHolder.img.setImageDrawable(drawable);
                    }
                });

            }
        }).start();
    }


}
