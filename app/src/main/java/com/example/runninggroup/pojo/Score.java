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
public class Score implements Serializable {
    private Integer id;
    private User user;
    private double scoreNum;
    private String ScoreType;
    private Timestamp createTime;
    private Timestamp updateTime;
}
