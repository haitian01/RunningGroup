package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.Comment;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.request.PostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

public class CommentDao {
    public static List<Comment> selectCommentByFriendCircleId (int friendCircleId) {
        String json = PostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_COMMENT, "friendCircleId=" + friendCircleId);
        return JSONArray.parseArray(json, Comment.class);
    }

    public static Integer insertComment (Comment comment) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_COMMENT, JSON.toJSONString(comment));
        return json == null || "null".equals(json) ? null : Integer.valueOf(json);
    }
    public static boolean deleteComment (Comment comment) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.DELETE_COMMENT, JSON.toJSONString(comment));
        return "succeed".equals(json) ? true : false;
    }
}
