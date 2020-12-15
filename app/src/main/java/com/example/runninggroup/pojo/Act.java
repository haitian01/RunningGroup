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
    private Integer id;
    private User user;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Double runLen;
    private String runType;
    private String place;
    private Integer shared;
    private Timestamp createTime;
    private Timestamp updateTime;
}
