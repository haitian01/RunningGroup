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
    private Integer id;
    private Team team;
    private Integer teamLevel;
    private String registerNum;
    private String username;
    private String password;
    private Integer sex;
    private String mail;
    private Double length;
    private Double teamLength;
    private Double score;
    private Double teamScore;
    private String signature;
    private Timestamp createTime;
    private Timestamp updateTime;


}
