package com.infoDiscover.solution.arch.demo.template;

/**
 * Created by sun.
 */
public class ProgressTemplate {
    String progressId;
    String progressName;
    String starter;
    long startTime;
    long endTime;

    public ProgressTemplate(String progressId, String progressName, String starter, long startTime, long endTime) {
        this.progressId = progressId;
        this.progressName = progressName;
        this.starter = starter;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
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
