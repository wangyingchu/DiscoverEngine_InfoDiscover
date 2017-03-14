package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task13BudgetCost extends NewProjectBaseTask {
    //String departmentId = "CostControl_Department";

    double yubeifei_yusuan; // 预备费（预算）
    double zaojiazongjine_yusuan; // 造价总金额（预算）
    double jiansheqitafei_yusuan; // 工程建设其他费（预算）
    Date zaojiawanchengriqi_yusuan; // 预算造价完成日期
    double jiananfei_yusuan; // 建安工程费（预算）

    public Task13BudgetCost(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getYubeifei_yusuan() {
        return yubeifei_yusuan;
    }

    public void setYubeifei_yusuan(double yubeifei_yusuan) {
        this.yubeifei_yusuan = yubeifei_yusuan;
    }

    public double getZaojiazongjine_yusuan() {
        return zaojiazongjine_yusuan;
    }

    public void setZaojiazongjine_yusuan(double zaojiazongjine_yusuan) {
        this.zaojiazongjine_yusuan = zaojiazongjine_yusuan;
    }

    public double getJiansheqitafei_yusuan() {
        return jiansheqitafei_yusuan;
    }

    public void setJiansheqitafei_yusuan(double jiansheqitafei_yusuan) {
        this.jiansheqitafei_yusuan = jiansheqitafei_yusuan;
    }

    public Date getZaojiawanchengriqi_yusuan() {
        return zaojiawanchengriqi_yusuan;
    }

    public void setZaojiawanchengriqi_yusuan(Date zaojiawanchengriqi_yusuan) {
        this.zaojiawanchengriqi_yusuan = zaojiawanchengriqi_yusuan;
    }

    public double getJiananfei_yusuan() {
        return jiananfei_yusuan;
    }

    public void setJiananfei_yusuan(double jiananfei_yusuan) {
        this.jiananfei_yusuan = jiananfei_yusuan;
    }
}
