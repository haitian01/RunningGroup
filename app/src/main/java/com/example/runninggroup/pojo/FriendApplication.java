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
    private Integer id;
    private User user;
    private User from;
    private User to;
    private Integer state;
    private String applicationMsg;
    private Timestamp createTime;
    private Timestamp updateTime;
}
