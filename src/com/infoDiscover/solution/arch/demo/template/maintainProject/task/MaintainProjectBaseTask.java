package com.infoDiscover.solution.arch.demo.template.maintainProject.task;

import com.infoDiscover.solution.arch.demo.template.TaskTemplate;

/**
 * Created by sun.
 */
public class MaintainProjectBaseTask extends TaskTemplate {

    public MaintainProjectBaseTask(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }
}
