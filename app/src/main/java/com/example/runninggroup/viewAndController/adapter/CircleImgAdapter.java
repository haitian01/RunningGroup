package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;

public class CircleImgAdapter extends RecyclerView.Adapter<CircleImgAdapter.InnerHolder> {
    private FriendCircle mFriendCircle;
    private Activity mActivity;
    public CircleImgAdapter (FriendCircle friendCircle, Activity activity) {
        mFriendCircle = friendCircle;
        mActivity = activity;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = View.inflate(parent.getContext(), R.layout.helper_write_normal, null);

        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        setCircleImg(holder, position);

    }
//    public interface ImgGetListener {
//        void imgGet();
//    }
//    public void setImgGetListener (ImgGetListener imgGetListener) {
//        mImgGetListener = imgGetListener;
//    }

    private void setCircleImg(InnerHolder holder, int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable =  null;
                if (mFriendCircle != null) {
                    for (int i = 0; i < 100; i++) {
                        if (drawable == null) drawable = ImgGet.getImg(ImgNameUtil.getCircleImgName(mFriendCircle.getId(), position));
                        else break;
                    }
                }
                if (drawable != null) {
                    Drawable finalDrawable = drawable;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.img.setImageDrawable(finalDrawable);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return mFriendCircle != null ? mFriendCircle.getImgNum() : 0;
    }
    public class InnerHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            init();
            initEvent();
        }

        private void initEvent() {
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        private void init() {
            img = itemView.findViewById(R.id.img);
        }
    }
}
