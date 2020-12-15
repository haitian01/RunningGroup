package com.example.runninggroup.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    private Integer id;
    private FriendCircle friendCircle;
    private User from;
    private User to;
    private String message;
    private Timestamp createTime;
    private Timestamp updateTime;
}
