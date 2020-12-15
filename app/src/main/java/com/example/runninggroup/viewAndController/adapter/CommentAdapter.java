package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.controller.CommentController;
import com.example.runninggroup.pojo.Comment;
import com.example.runninggroup.pojo.FriendCircle;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Comment> mCommentList;
    private static final int COMMENT = 0;
    private static final int COMMENT_BACK = 1;
    private CommentOnClickListener commentOnClickListener;
    private Activity mActivity;
    public CommentAdapter (List<Comment> commentList, Activity activity) {
        mCommentList = commentList;
        mActivity = activity;

    }

    /**
     * 评论点击事件接口
     */
    public interface CommentOnClickListener {
        //评论点击事件
        void messageOnClick (Comment comment);
        //评论长按
        void messageOnLongClick (Comment comment);
        //from
        void fromOnClick(Comment comment);
        //to
        void toOnClick(Comment comment);
    }
    public void setCommentOnClickListener (CommentOnClickListener commentOnClickListener) {
        this.commentOnClickListener = commentOnClickListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == COMMENT) {
            view = mActivity.getLayoutInflater().inflate(R.layout.helper_comment, parent, false);
             return new CommentHolder(view);
        }else {
            view = mActivity.getLayoutInflater().inflate(R.layout.helper_comment_back, parent, false);
            return new BackHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setData(holder, position);
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Comment comment = mCommentList.get(position);
        return comment == null || comment.getTo() == null ? COMMENT : COMMENT_BACK;
    }


    //无回复的holder
    class CommentHolder extends RecyclerView.ViewHolder {
        TextView fromTxt, messageTxt;
        LinearLayout comment;
        Comment mComment;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }
        public void initView() {
            fromTxt = itemView.findViewById(R.id.from);
            messageTxt = itemView.findViewById(R.id.message);
            comment = itemView.findViewById(R.id.comment);
            initEvent();
        }

        private void initEvent() {
            /**
             * 内容点击事件
             */
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentOnClickListener != null) {
                        commentOnClickListener.messageOnClick(mComment);
                    }
                }
            });
            /**
             * 内容长按事件
             */
            comment.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (commentOnClickListener != null) {
                        commentOnClickListener.messageOnLongClick(mComment);
                    }
                    return true;
                }
            });

            /**
             * from点击事件
             */
            fromTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentOnClickListener != null) {
                        commentOnClickListener.fromOnClick(mComment);
                    }
                }
            });


        }
    }

    //有回复的holder
    class BackHolder extends RecyclerView.ViewHolder {
        TextView fromTxt, messageTxt, toTxt;
        LinearLayout comment;
        Comment mComment;
        public BackHolder(@NonNull View itemView) {
            super(itemView);
            initView();
            initEvent();
        }
        public void initView() {
            fromTxt = itemView.findViewById(R.id.from);
            messageTxt = itemView.findViewById(R.id.message);
            toTxt = itemView.findViewById(R.id.to);
            comment = itemView.findViewById(R.id.comment);
        }

        private void initEvent() {
            /**
             * 内容点击事件
             */
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentOnClickListener != null) {
                        commentOnClickListener.messageOnClick(mComment);
                    }
                }
            });
            /**
             * 内容长按事件
             */
            comment.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (commentOnClickListener != null) {
                        commentOnClickListener.messageOnLongClick(mComment);
                    }
                    return true;
                }
            });


            /**
             * from点击事件
             */
            fromTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentOnClickListener != null) {
                        commentOnClickListener.fromOnClick(mComment);
                    }
                }
            });

            /**
             * to点击事件
             */
            toTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentOnClickListener != null) {
                        commentOnClickListener.toOnClick(mComment);
                    }
                }
            });


        }
    }


    public void setData (RecyclerView.ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        if (holder instanceof CommentHolder) {
            ((CommentHolder) holder).mComment = comment;
           ((CommentHolder) holder).fromTxt.setText(comment.getFrom().getUsername());
           ((CommentHolder) holder).messageTxt.setText(comment.getMessage());
       }else if (holder instanceof BackHolder) {
            ((BackHolder) holder).mComment = comment;
            ((BackHolder) holder).fromTxt.setText(comment.getFrom().getUsername());
            ((BackHolder) holder).messageTxt.setText(comment.getMessage());
            ((BackHolder) holder).toTxt.setText(comment.getTo().getUsername());
        }
    }

}
