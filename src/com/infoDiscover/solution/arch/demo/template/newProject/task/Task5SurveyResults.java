package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task5SurveyResults extends NewProjectBaseTask {
    // String departmentId = "Engineering_Department";
    String cehuikanchashejirenyuan; // 勘察设计人员
    String cehuikanchajishuren; // 勘察技术人员
    Date cehuikanchawanchengriqi; // 勘察完成日期
    String cehuikanchamiaoshu; // 勘察成果描述

    public Task5SurveyResults(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getCehuikanchashejirenyuan() {
        return cehuikanchashejirenyuan;
    }

    public void setCehuikanchashejirenyuan(String cehuikanchashejirenyuan) {
        this.cehuikanchashejirenyuan = cehuikanchashejirenyuan;
    }

    public String getCehuikanchajishuren() {
        return cehuikanchajishuren;
    }

    public void setCehuikanchajishuren(String cehuikanchajishuren) {
        this.cehuikanchajishuren = cehuikanchajishuren;
    }

    public Date getCehuikanchawanchengriqi() {
        return cehuikanchawanchengriqi;
    }

    public void setCehuikanchawanchengriqi(Date cehuikanchawanchengriqi) {
        this.cehuikanchawanchengriqi = cehuikanchawanchengriqi;
    }

    public String getCehuikanchamiaoshu() {
        return cehuikanchamiaoshu;
    }

    public void setCehuikanchamiaoshu(String cehuikanchamiaoshu) {
        this.cehuikanchamiaoshu = cehuikanchamiaoshu;
    }
}
