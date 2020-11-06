package com.example.runninggroup.pojo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamApplication {
    private Integer id;
    private Team team;
    private User user;
    private String applicationMsg;
    private Integer state;
    private Timestamp createTime;
    private Timestamp updateTime;
}
