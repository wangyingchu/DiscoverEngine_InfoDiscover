package com.infoDiscover.solution.arch.demo.template.newProject.task;

import com.infoDiscover.solution.arch.demo.template.TaskTemplate;

/**
 * Created by sun.
 */
public class NewProjectBaseTask extends TaskTemplate {

    public NewProjectBaseTask(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }
}
