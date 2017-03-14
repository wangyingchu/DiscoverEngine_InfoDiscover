package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task2ProjectStart extends NewProjectBaseTask {
    // String departmentId = "Proerty_Department";

    Date projectqidongriqi; // 项目启动日期


    public Task2ProjectStart(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getProjectqidongriqi() {
        return projectqidongriqi;
    }

    public void setProjectqidongriqi(Date projectqidongriqi) {
        this.projectqidongriqi = projectqidongriqi;
    }
}
