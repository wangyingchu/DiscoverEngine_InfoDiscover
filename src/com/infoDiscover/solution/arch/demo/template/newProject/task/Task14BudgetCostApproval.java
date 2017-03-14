package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task14BudgetCostApproval extends NewProjectBaseTask {
    //String departmentId = "Finance_Bureau";
    double jianangongchengfei_shending; // 建安工程费（审定）
    double yubeifen_shending; // 预备费（审定）
    double zaojiazongjine_shending; // 造价总金额（审定）
    Date zaojiashenpiriqi; // 预算造价审批完成日期
    double jiansheqifafei; // 工程建设其他费（审定）

    public Task14BudgetCostApproval(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getJianangongchengfei_shending() {
        return jianangongchengfei_shending;
    }

    public void setJianangongchengfei_shending(double jianangongchengfei_shending) {
        this.jianangongchengfei_shending = jianangongchengfei_shending;
    }

    public double getYubeifen_shending() {
        return yubeifen_shending;
    }

    public void setYubeifen_shending(double yubeifen_shending) {
        this.yubeifen_shending = yubeifen_shending;
    }

    public double getZaojiazongjine_shending() {
        return zaojiazongjine_shending;
    }

    public void setZaojiazongjine_shending(double zaojiazongjine_shending) {
        this.zaojiazongjine_shending = zaojiazongjine_shending;
    }

    public Date getZaojiashenpiriqi() {
        return zaojiashenpiriqi;
    }

    public void setZaojiashenpiriqi(Date zaojiashenpiriqi) {
        this.zaojiashenpiriqi = zaojiashenpiriqi;
    }

    public double getJiansheqifafei() {
        return jiansheqifafei;
    }

    public void setJiansheqifafei(double jiansheqifafei) {
        this.jiansheqifafei = jiansheqifafei;
    }
}
