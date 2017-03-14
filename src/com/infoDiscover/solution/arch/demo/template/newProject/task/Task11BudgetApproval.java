package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task11BudgetApproval extends NewProjectBaseTask {
    // String departmentId = "BuildingAndEnvironment_Bureau";
    double zonggaisuanfei_pifu; // 总概算费(批复)
    double gongchengjiansheqitafei_pifu; // 工程建设其他费(批复)
    Date gaisuanshenpiriqi; // 概算审批日期
    double yubeifei_pifu; // 预备费(批复)
    double jianangongchengfei_pifu; // 建安工程费(批复)

    public Task11BudgetApproval(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getZonggaisuanfei_pifu() {
        return zonggaisuanfei_pifu;
    }

    public void setZonggaisuanfei_pifu(double zonggaisuanfei_pifu) {
        this.zonggaisuanfei_pifu = zonggaisuanfei_pifu;
    }

    public double getGongchengjiansheqitafei_pifu() {
        return gongchengjiansheqitafei_pifu;
    }

    public void setGongchengjiansheqitafei_pifu(double gongchengjiansheqitafei_pifu) {
        this.gongchengjiansheqitafei_pifu = gongchengjiansheqitafei_pifu;
    }

    public Date getGaisuanshenpiriqi() {
        return gaisuanshenpiriqi;
    }

    public void setGaisuanshenpiriqi(Date gaisuanshenpiriqi) {
        this.gaisuanshenpiriqi = gaisuanshenpiriqi;
    }

    public double getYubeifei_pifu() {
        return yubeifei_pifu;
    }

    public void setYubeifei_pifu(double yubeifei_pifu) {
        this.yubeifei_pifu = yubeifei_pifu;
    }

    public double getJianangongchengfei_pifu() {
        return jianangongchengfei_pifu;
    }

    public void setJianangongchengfei_pifu(double jianangongchengfei_pifu) {
        this.jianangongchengfei_pifu = jianangongchengfei_pifu;
    }
}
