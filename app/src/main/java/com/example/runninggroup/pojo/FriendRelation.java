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
public class FriendRelation implements Serializable {
    private int id;
    private User user;
    private User friend;
    private String alias;
    private Timestamp createTime;
    private Timestamp updateTime;
}
