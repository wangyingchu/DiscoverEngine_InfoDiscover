package com.businessExtension.arch.demo.template.maintainProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task10Jungongyanshou extends MaintainProjectBaseTask {
//    String departmentId = "Engineering_Department";

    private Date yanshouriqi; // 验收日期　DATE
    private String yanshoupingjia; // 验收评价　STRING
    private boolean shifouwancheng = false; //是否通过验收　BOOLEAN

    public Task10Jungongyanshou(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getYanshouriqi() {
        return yanshouriqi;
    }

    public void setYanshouriqi(Date yanshouriqi) {
        this.yanshouriqi = yanshouriqi;
    }

    public String getYanshoupingjia() {
        return yanshoupingjia;
    }

    public void setYanshoupingjia(String yanshoupingjia) {
        this.yanshoupingjia = yanshoupingjia;
    }

    public boolean isShifouwancheng() {
        return shifouwancheng;
    }

    public void setShifouwancheng(boolean shifouwancheng) {
        this.shifouwancheng = shifouwancheng;
    }
}
