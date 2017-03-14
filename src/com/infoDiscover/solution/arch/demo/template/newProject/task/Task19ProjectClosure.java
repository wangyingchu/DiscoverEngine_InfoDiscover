package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task19ProjectClosure extends NewProjectBaseTask {
    //String departmentId = "General_Manager";
    Date projectguanbiriqi; //项目关闭日期

    public Task19ProjectClosure(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getProjectguanbiriqi() {
        return projectguanbiriqi;
    }

    public void setProjectguanbiriqi(Date projectguanbiriqi) {
        this.projectguanbiriqi = projectguanbiriqi;
    }
}
