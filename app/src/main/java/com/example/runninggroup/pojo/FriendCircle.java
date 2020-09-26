package com.example.runninggroup.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FriendCircle implements Serializable {
    private int id;
    private User user;
    private String circleMsg;
    private String circleImg;
    private Timestamp createTime;
    private Timestamp updateTime;
}
