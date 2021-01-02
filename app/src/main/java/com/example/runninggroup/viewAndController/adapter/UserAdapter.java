package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<User> mList;
    Activity mActivity;
    private OnItemClickListener mOnItemClickListener;
    public UserAdapter(List<User> list, Activity activity) {
            mList = list;
            mActivity = activity;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.helper_user, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) {
            InnerHolder viewHolder = (InnerHolder) holder;
            User user = mList.get(position);
            viewHolder.msg.setText(mList.get(position).getUsername() + "(" + mList.get(position).getRegisterNum() + ")");
            viewHolder.img.setImageResource(user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            viewHolder.userItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(position);
                }
            });
            setImg(viewHolder, user.getId());
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


    private class InnerHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView msg;
        private RelativeLayout userItem;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }
        public void initView() {
            img = itemView.findViewById(R.id.img);
            msg = itemView.findViewById(R.id.msg);
            userItem = itemView.findViewById(R.id.user_item);
        }
    }
    public void setImg (InnerHolder viewHolder, int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = ImgGet.getImg(ImgNameUtil.getUserHeadImgName(id));
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null) viewHolder.img.setImageDrawable(drawable);
                    }
                });
            }
        }).start();
    }
}
