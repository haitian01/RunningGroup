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
public class FriendApplication implements Serializable {
    private int id;
    private User user;
    private User from;
    private User to;
    private int state;
    private String applicationMsg;
    private Timestamp createTime;
    private Timestamp updateTime;
}
