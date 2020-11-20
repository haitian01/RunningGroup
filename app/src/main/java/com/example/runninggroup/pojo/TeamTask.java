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
public class TeamTask implements Serializable {
    private Integer id;
    private Team team;
    private User user;
    private String taskMsg;
    private String taskImg;
    private Timestamp createTime;
    private Timestamp updateTime;
}
