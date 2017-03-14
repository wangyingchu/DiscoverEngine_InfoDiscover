package com.infoDiscover.solution.arch.demo.template;

/**
 * Created by sun.
 */
public class TaskTemplate {
    private String progressId;
    private String taskId;
    private String taskName;
    private String departmentId;
    private String assignee;
    private long startTime;
    private long endTime;

    public TaskTemplate(String progressId, String taskId, String taskName, String departmentId,
                        String assignee, long startTime) {
        this.progressId = progressId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.departmentId = departmentId;
        this.assignee = assignee;
        this.startTime = startTime;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
