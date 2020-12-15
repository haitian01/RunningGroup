package com.example.runninggroup.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.runninggroup.pojo.Act;
import com.example.runninggroup.request.JsonPostRequest;
import com.example.runninggroup.util.ConstantUtil;

import java.util.List;

/**
 * 发送网络请求
 * 对接Act数据表操作
 */
public class ActDao {
    /**
     * 根据条件获取Act集合
     * @param act
     * @return
     */
    public static List<Act> selectAct (Act act) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.GET_ACT, JSON.toJSONString(act));
        return JSONArray.parseArray(json, Act.class);
    }

    /**
     * 插入一条打卡记录
     */
    public static boolean addAct (Act act) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.ADD_ACT, JSON.toJSONString(act));
        return "succeed".equals(json);
    }

    /**
     * 更新打卡记录（分享状态改变）
     */

    public static boolean updateAct (Act act) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.UPDATE_ACT, JSON.toJSONString(act));
        return "succeed".equals(json);
    }

    /**
     * 删除Act
     */
    public static boolean deleteAct (Act act) {
        String json = JsonPostRequest.postRequest(ConstantUtil.URL + ConstantUtil.DELETE_ACT, JSON.toJSONString(act));
        return "succeed".equals(json);
    }

}
