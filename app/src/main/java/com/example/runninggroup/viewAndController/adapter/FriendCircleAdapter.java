package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.CommentController;
import com.example.runninggroup.pojo.Comment;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.viewAndController.CardPersonal;
import com.example.runninggroup.viewAndController.FriendMessage;

import java.util.List;

public class FriendCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FriendCircle> mList;
    private static final int NORMAL = 0;
    private static final int LOAD = 1;
    OnRefreshListener onRefreshListener;
    ControllerOnClickListener controllerOnClickListener;
    private ImgBackListener imgBackListener;
    private Activity mActivity;
    private CommentOnClickListener commentOnClickListener;
    public FriendCircleAdapter (List<FriendCircle> list, Activity activity) {
        mList = list;
        mActivity = activity;
    }

    /**
     * 评论点击事件接口
     */
    public interface CommentOnClickListener {
        //评论点击事件
        void messageOnClick (Comment comment, InnerHolder innerHolder);
        //评论长按
        void messageOnLongClick (Comment comment, InnerHolder innerHolder);
        //from
        void fromOnClick(Comment comment, InnerHolder innerHolder);
        //to
        void toOnClick(Comment comment, InnerHolder innerHolder);


    }
    public void setCommentOnClickListener (CommentOnClickListener commentOnClickListener) {
        this.commentOnClickListener = commentOnClickListener;
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

        /**
         * 右上角叉号点击事件
         * @param innerHolder
         */
        void deleteOnClick(InnerHolder innerHolder);
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



    public class InnerHolder extends RecyclerView.ViewHolder implements CommentController.CommentControllerInterface{
        public FriendCircle mFriendCircle;
        public RelativeLayout zan, comment, delete;
        public ImageView headImg, zanImg;
        public TextView username;
        public TextView msg;
        public TextView commentNum;
        public TextView zanNum;
        public RecyclerView imgRecy,commentRecy;
        public RecyclerView imgRecyFour;
        public List<Comment> mCommentList;
        public CommentAdapter commentAdapter;
        private CommentController mCommentController = new CommentController(this);
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
            commentRecy = itemView.findViewById(R.id.comment_recy);
            imgRecyFour = itemView.findViewById(R.id.img_recy_four);
            delete = itemView.findViewById(R.id.delete);
            /**
             * 设置comment的RecycleView
             */
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
            commentRecy.setLayoutManager(layoutManager);
            //设置边距
            ItemDecoration itemDecoration = new ItemDecoration(10);
            commentRecy.addItemDecoration(itemDecoration);
            initEvent();



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
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (controllerOnClickListener != null) controllerOnClickListener.deleteOnClick(InnerHolder.this);
                }
            });
        }

        @Override
        public void selectCommentByFriendCircleIdBack(List<Comment> commentList) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCommentList = commentList;
                    commentAdapter = new CommentAdapter(mCommentList, mActivity);
                    /**
                     * 评论点击事件
                     */
                    commentAdapter.setCommentOnClickListener(new CommentAdapter.CommentOnClickListener() {
                        @Override
                        public void messageOnClick(Comment comment) {
                            if (commentOnClickListener != null) commentOnClickListener.messageOnClick(comment, InnerHolder.this);
                        }

                        @Override
                        public void messageOnLongClick(Comment comment) {
                            if (commentOnClickListener != null) commentOnClickListener.messageOnLongClick(comment, InnerHolder.this);
                        }

                        @Override
                        public void fromOnClick(Comment comment) {
                            if (commentOnClickListener != null) commentOnClickListener.fromOnClick(comment, InnerHolder.this);
                        }

                        @Override
                        public void toOnClick(Comment comment) {
                            if (commentOnClickListener != null) commentOnClickListener.toOnClick(comment, InnerHolder.this);
                        }
                    });
                    commentRecy.setAdapter(commentAdapter);
                }
            });
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

        private void initEventWithData() {

            headImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cache.friend = mFriendCircle.getUser();
                    Intent intent = new Intent(mActivity, FriendMessage.class);
                    mActivity.startActivity(intent);
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


            /**
             * 如果用户id == 动态id deleteImg显示
             */
            if (Cache.user.getId() == friendCircle.getUser().getId()) delete.setVisibility(View.VISIBLE);


            //获取评论
            mCommentController.selectCommentByFriendCircleId(friendCircle);


            /**
             * 动态图片的adapter设置 CircleImgAdapter
             */

            //设置adapter
            CircleImgAdapter circleImgAdapter = new CircleImgAdapter(mFriendCircle, mActivity);
            GridLayoutManager gridLayoutManager = null;
            int num = friendCircle.getImgNum();

            //1张图片，原比例
            //2-3张  3*n
            //4张  2*2
            //5 - 9 3*n
            if (num == 4) {
                gridLayoutManager = new GridLayoutManager(mActivity, 2);
                imgRecyFour.setAdapter(circleImgAdapter);
                imgRecyFour.setLayoutManager(gridLayoutManager);
                imgRecyFour.setVisibility(View.VISIBLE);
                imgRecy.setVisibility(View.GONE);
            }
            else {
                imgRecy.setAdapter(circleImgAdapter);
                if ((num <= 3 && num > 1) || (num >= 5 && num <= 9)) gridLayoutManager = new GridLayoutManager(mActivity, 3);
                else if (num == 1) gridLayoutManager = new GridLayoutManager(mActivity, 1);
                imgRecy.setLayoutManager(gridLayoutManager);
                imgRecy.setAdapter(circleImgAdapter);
                imgRecyFour.setVisibility(View.GONE);
                imgRecy.setVisibility(View.VISIBLE);
            }




            initEventWithData();
        }






        private void setHeadImg(FriendCircle friendCircle) {
            headImg.setImageResource(friendCircle.getUser().getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Drawable drawable = null;
                    if (friendCircle != null && friendCircle.getUser() != null)
                        for (int i = 0; i < 10; i++) {
                            if (drawable != null) break;
                            drawable = ImgGet.getImg(ImgNameUtil.getUserHeadImgName(friendCircle.getUser().getId()));
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
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
    /**
     * RecycleView设置间距
     */
    public class ItemDecoration extends RecyclerView.ItemDecoration {
        public int it;
        public ItemDecoration(int it){
            this.it=it;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=it;
            outRect.right=it;
            outRect.bottom=it;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=it;
            }
        }
    }
}
