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
public class Team implements Serializable {
    private Integer id;
    private String registerNum;
    private String campus;
    private String college;
    private String teamImg;
    private User user;//团长
    private String teamName;
    private Integer teamSize;
    private String teamSlogan;
    private Long score;
    private Long length;
    private Timestamp createTime;
    private Timestamp updateTime;
}
