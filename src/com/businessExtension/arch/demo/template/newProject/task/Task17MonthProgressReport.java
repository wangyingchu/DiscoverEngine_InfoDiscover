package com.businessExtension.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task17MonthProgressReport extends NewProjectBaseTask {
    // String departmentId = "Engineering_Department";
    Date shenheriqi; // 审核日期
    String anquanjiancejieguo; // 安全检测结果
    double yuezijinfeiyong; // 月资金费用
    String zhiliangjiancejieguo; // 质量检测结果

    public Task17MonthProgressReport(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getShenheriqi() {
        return shenheriqi;
    }

    public void setShenheriqi(Date shenheriqi) {
        this.shenheriqi = shenheriqi;
    }

    public String getAnquanjiancejieguo() {
        return anquanjiancejieguo;
    }

    public void setAnquanjiancejieguo(String anquanjiancejieguo) {
        this.anquanjiancejieguo = anquanjiancejieguo;
    }

    public double getYuezijinfeiyong() {
        return yuezijinfeiyong;
    }

    public void setYuezijinfeiyong(double yuezijinfeiyong) {
        this.yuezijinfeiyong = yuezijinfeiyong;
    }

    public String getZhiliangjiancejieguo() {
        return zhiliangjiancejieguo;
    }

    public void setZhiliangjiancejieguo(String zhiliangjiancejieguo) {
        this.zhiliangjiancejieguo = zhiliangjiancejieguo;
    }
}
