package com.example.runninggroup.controller;

import com.example.runninggroup.dao.CommentDao;
import com.example.runninggroup.pojo.Comment;
import com.example.runninggroup.pojo.FriendCircle;

import java.util.List;

public class CommentController {
    private CommentControllerInterface mCommentControllerInterface;
    public CommentController (CommentControllerInterface commentControllerInterface) {
        mCommentControllerInterface = commentControllerInterface;
    }

    //根据朋友圈id查找评论列表
    public void selectCommentByFriendCircleId (FriendCircle friendCircle) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Comment> commentList = null;
                for (int i = 0; i < 10; i++) {
                    if (commentList != null) break;
                    commentList  = CommentDao.selectCommentByFriendCircleId(friendCircle.getId());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mCommentControllerInterface.selectCommentByFriendCircleIdBack(commentList);
            }
        }).start();
    }

    //评论
    public void insertComment (Comment comment) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Integer res = CommentDao.insertComment(comment);
                if (res == null) mCommentControllerInterface.insertCommentBack(false);
                else {
                    comment.setId(res);
                    mCommentControllerInterface.insertCommentBack(true);
                }
            }
        }).start();
    }

    //删除评论
    public void deleteComment (Comment comment) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean res = CommentDao.deleteComment(comment);
                mCommentControllerInterface.deleteCommentBack(res);
            }
        }).start();
    }



    public interface CommentControllerInterface {
        default void selectCommentByFriendCircleIdBack (List<Comment> commentList){};
        default void insertCommentBack (boolean res){};

        default void deleteCommentBack(boolean res) {};
    }
}
