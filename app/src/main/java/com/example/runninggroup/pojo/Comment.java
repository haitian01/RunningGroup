package com.example.runninggroup.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    private int id;
    private int friendCircleId;
    private int fromId;
    private int toId;
    private String message;
    private Timestamp createTime;
    private Timestamp updateTime;
}
