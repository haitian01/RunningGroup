package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.viewAndController.ImageDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CircleImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private FriendCircle mFriendCircle;
    private Activity mActivity;
    private static final int NINE= 0;
    private static final int ONE = 1;
    private static final int FOUR = 2;
    public ConcurrentHashMap<Integer ,Drawable> mDrawables = new ConcurrentHashMap<>();
    public CircleImgAdapter (FriendCircle friendCircle, Activity activity) {
        mFriendCircle = friendCircle;
        mActivity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = null;
        if (viewType == NINE) {
            view = View.inflate(parent.getContext(), R.layout.helper_write_normal, null);
            return new InnerHolder(view);
        }else if (viewType == ONE) {
            view = View.inflate(parent.getContext(), R.layout.helper_circle_one, null);
        }

        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setCircleImg(holder, position);
        setImgOnClickListener(holder, position);

    }

    @Override
    public int getItemViewType(int position) {
        if (mFriendCircle.getImgNum() == 1) return ONE;
        else return NINE;
    }

    @Override
    public int getItemCount() {
        return mFriendCircle != null ? mFriendCircle.getImgNum() : 0;
    }

    /**
     * 网络请求图片
     * @param holder
     * @param position
     */
    private void setCircleImg(RecyclerView.ViewHolder holder, int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable =  null;
                if (mFriendCircle != null) {
                    for (int i = 0; i < 10; i++) {
                        if (drawable == null) {
                            drawable = ImgGet.getImg(ImgNameUtil.getCircleImgName(mFriendCircle.getId(), position));
                        }
                        else break;
                    }
                    if (drawable != null) mDrawables.put(position, drawable);
                }
                if (drawable != null) {
                    Drawable finalDrawable = drawable;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (holder instanceof InnerHolder) {
                                ((InnerHolder) holder).img.setImageDrawable(finalDrawable);
                            }else if (holder instanceof OneImgInnerHolder) {
                                ((OneImgInnerHolder) holder).img.setImageDrawable(finalDrawable);
                            }
                        }
                    });
                }
            }
        }).start();
    }


    /**
     * 适配2-3， 5-9张图片的ViewHolder
     */
    public class InnerHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        private void init() {
            img = itemView.findViewById(R.id.img);
        }
    }

    /**
     * 适配1张图片的ViewHolder
     */
    public class OneImgInnerHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public OneImgInnerHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }


        private void init() {
            img = itemView.findViewById(R.id.img);
        }
    }


    /**
     * 动态图片点击事件
     * @param holder 可能为不同的ViewHolder
     * @param position 图片的位置，第几张
     */
    private void setImgOnClickListener (RecyclerView.ViewHolder holder, int position) {
        ImageView img = null;
        if (holder instanceof InnerHolder) img = ((InnerHolder) holder).img;
        else if (holder instanceof OneImgInnerHolder) img = ((OneImgInnerHolder) holder).img;
        if (img != null) {
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cache.sDrawables = mDrawables;
                    Intent intent = new Intent(mActivity, ImageDetail.class);
                    intent.putExtra("num", position);
                    mActivity.startActivity(intent);
                }
            });
        }


    }
}
