package com.infoDiscover.solution.arch.progress.fact;

/**
 * Created by sun.
 */
public class ProgressFact {
    String factType;
    String progressId;
    String content;

    public ProgressFact(String factType, String progressId, String content) {
        this.factType = factType;
        this.progressId = progressId;
        this.content = content;
    }

    public String getFactType() {
        return factType;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
