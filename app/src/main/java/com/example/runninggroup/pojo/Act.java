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
public class Act implements Serializable {
    private int id;
    private User user;
    private Timestamp beginTime;
    private Timestamp endTime;
    private int runLen;
    private String runType;
    private String actImg;
    private String beginPlace;
    private String endPlace;
    private Timestamp createTime;
    private Timestamp updateTime;
}
