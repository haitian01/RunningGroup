package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class FriendCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FriendCircle> mList;
    private static final int NORMAL = 0;
    private static final int LOAD = 1;
    OnRefreshListener onRefreshListener;
    ControllerOnClickListener controllerOnClickListener;
    private ImgBackListener imgBackListener;
    private Activity mActivity;
    public FriendCircleAdapter (List<FriendCircle> list, Activity activity) {
        mList = list;
        mActivity = activity;
    }

    //上拉刷新
    public interface OnRefreshListener {
        void onUpPullRefresh(LoadHolder loadHolder);
    }
    public void setOnRefreshListener (OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }



    //点击事件
    public interface ControllerOnClickListener {
        //点赞
        default void zanOnClick(InnerHolder innerHolder){};
        //评论
        default void commentOnClick(InnerHolder innerHolder){};
    }
    //设置控件点击事件
    public void setControllerOnClickListener (ControllerOnClickListener controllerOnClickListener) {
        this.controllerOnClickListener = controllerOnClickListener;
    }

    //图片回调
    public interface ImgBackListener {
        default void imgBack (Drawable drawable, InnerHolder innerHolder) {}
    }
    public void setImgBackListener (ImgBackListener imgBackListener) {
        this.imgBackListener = imgBackListener;
    }






    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == NORMAL) {
            view  = View.inflate(parent.getContext(), R.layout.helper_friend_circle, null);
            return new InnerHolder(view);
        }
        else {
            view  = View.inflate(parent.getContext(), R.layout.helper_load_more, null);
            return new LoadHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof InnerHolder) ((InnerHolder) holder).setData(mList.get(position));
        else if (holder instanceof LoadHolder) ((LoadHolder) holder).update(LoadHolder.LOAD);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) return LOAD;
        else return NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size() + 1;
        return 0;
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        public FriendCircle mFriendCircle;
        public RelativeLayout zan, comment;
        public ImageView headImg, zanImg;
        public TextView username;
        public TextView msg;
        public TextView commentNum;
        public TextView zanNum;
        public RecyclerView imgRecy;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            headImg = itemView.findViewById(R.id.headImg);
            username = itemView.findViewById(R.id.username);
            msg = itemView.findViewById(R.id.msg);
            commentNum = itemView.findViewById(R.id.comment_num);
            zanNum = itemView.findViewById(R.id.zan_num);
            zan = itemView.findViewById(R.id.zan);
            comment = itemView.findViewById(R.id.comment);
            zanImg = itemView.findViewById(R.id.zan_img);
            imgRecy = itemView.findViewById(R.id.img_recy);


            initEvent();

        }

        private void setZanImg(FriendCircle friendCircle) {
            String zanGroup = friendCircle.getZanGroup();
            if (zanGroup == null || zanGroup.length() == 0) zanImg.setImageResource(R.drawable.zan);
            else {
                String[] split = zanGroup.split(",");
                for (String s : split) {
                    //已经点过赞
                    if (s.equals(Cache.user.getId() + ""))  {
                        zanImg.setImageResource(R.drawable.zan_after);
                        return;
                    }
                }
                zanImg.setImageResource(R.drawable.zan);
            }

        }

        private void initEvent() {
            zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (controllerOnClickListener != null) controllerOnClickListener.zanOnClick(InnerHolder.this);
                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (controllerOnClickListener != null) controllerOnClickListener.commentOnClick(InnerHolder.this);
                }
            });
        }

        public void setData(FriendCircle friendCircle) {
            mFriendCircle = friendCircle;
            username.setText(friendCircle.getUser().getUsername());
            msg.setText(friendCircle.getCircleMsg());
            commentNum.setText(friendCircle.getCommentNum() + "");
            zanNum.setText(friendCircle.getZanNum() + "");
            setZanImg(friendCircle);
            setHeadImg(friendCircle);


            //设置adapter
            CircleImgAdapter circleImgAdapter = new CircleImgAdapter(mFriendCircle, mActivity);
            imgRecy.setAdapter(circleImgAdapter);
            GridLayoutManager gridLayoutManager = null;
            int num = friendCircle.getImgNum();

            //1张图片，原比例
            //2-3张  3*n
            //4张  2*2
            //5 - 9 3*n
            if (num <= 3 || (num >= 5 && num <= 9)) gridLayoutManager = new GridLayoutManager(mActivity, 3);
            else if (num == 4) gridLayoutManager = new GridLayoutManager(mActivity, 2);
            imgRecy.setLayoutManager(gridLayoutManager);
        }

        private void setHeadImg(FriendCircle friendCircle) {
            headImg.setImageResource(friendCircle.getUser().getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Drawable drawable = null;
                    if (friendCircle != null && friendCircle.getUser() != null)
                        drawable = ImgGet.getImg(ImgNameUtil.getUserHeadImgName(friendCircle.getUser().getId()));
                    imgBackListener.imgBack(drawable, InnerHolder.this);

                }
            }).start();
        }
    }
    public class LoadHolder extends RecyclerView.ViewHolder {
        TextView msg;
        RelativeLayout load;
        RelativeLayout loadFail;
        RelativeLayout out;
        public static final int LOAD = 0;
        public static final int LOAD_FAIL = 1;
        public static final int NORMAL= 2;
        public LoadHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msg);
            load = itemView.findViewById(R.id.load);
            loadFail = itemView.findViewById(R.id.load_fail);
            out = itemView.findViewById(R.id.out);
            loadFail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(LOAD);
                }
            });
        }

        public void update(int type) {
            if (type == LOAD) {
                out.setVisibility(View.VISIBLE);
                load.setVisibility(View.VISIBLE);
                loadFail.setVisibility(View.GONE);
                //接口回调更新界面
                onRefreshListener.onUpPullRefresh(this);
            }else if (type == LOAD_FAIL) {
                out.setVisibility(View.VISIBLE);
                load.setVisibility(View.GONE);
                loadFail.setVisibility(View.VISIBLE);
            }else if (type == NORMAL) {
                out.setVisibility(View.GONE);
            }
        }
    }
}
