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
public class User implements Serializable {
    private int id;
    private Team team;
    private int teamLevel;
    private String registerNum;
    private String username;
    private String password;
    private int sex;
    private String headImg;
    private String mail;
    private long length;
    private long score;
    private Timestamp createTime;
    private Timestamp updateTime;


}
