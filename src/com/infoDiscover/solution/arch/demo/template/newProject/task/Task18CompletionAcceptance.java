package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task18CompletionAcceptance extends NewProjectBaseTask {
    // String departmentId = "Engineering_Department";
    Date yanshouriqi; // 验收日期
    String yanshoudanwei; // 验收单位
    String yanshoupingjia; // 验收评价

    public Task18CompletionAcceptance(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getYanshouriqi() {
        return yanshouriqi;
    }

    public void setYanshouriqi(Date yanshouriqi) {
        this.yanshouriqi = yanshouriqi;
    }

    public String getYanshoudanwei() {
        return yanshoudanwei;
    }

    public void setYanshoudanwei(String yanshoudanwei) {
        this.yanshoudanwei = yanshoudanwei;
    }

    public String getYanshoupingjia() {
        return yanshoupingjia;
    }

    public void setYanshoupingjia(String yanshoupingjia) {
        this.yanshoupingjia = yanshoupingjia;
    }
}
