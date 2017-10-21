package com.businessExtension.arch.demo.template.maintainProject.task;

import com.businessExtension.arch.demo.template.TaskTemplate;

/**
 * Created by sun.
 */
public class MaintainProjectBaseTask extends TaskTemplate {

    public MaintainProjectBaseTask(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }
}
