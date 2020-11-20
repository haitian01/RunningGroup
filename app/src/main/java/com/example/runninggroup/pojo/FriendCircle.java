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
    private Integer id;
    private User user;
    private String circleMsg;
    private Integer commentNum;
    private Integer zanNum;
    private String zanGroup;
    private Integer imgNum;
    private Timestamp createTime;
    private Timestamp updateTime;
}
