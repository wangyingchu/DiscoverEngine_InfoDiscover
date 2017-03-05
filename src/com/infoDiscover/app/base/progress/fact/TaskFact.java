package com.infoDiscover.app.base.progress.fact;

/**
 * Created by sun.
 */
public class TaskFact {
    String progressId;
    String taskId;
    String content;

    public TaskFact(String progressId, String taskId, String content) {
        this.progressId = progressId;
        this.taskId = taskId;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
